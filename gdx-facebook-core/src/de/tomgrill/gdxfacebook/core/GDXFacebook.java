/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.tomgrill.gdxfacebook.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


public abstract class GDXFacebook implements GDXFacebookCallback<JsonResult> {

	protected GDXFacebookConfig config;
	protected Preferences preferences;
	protected GDXFacebookAccessToken accessToken;
	protected GDXFacebookCallback callback;
	protected Array<String> permissions;

	public GDXFacebook(GDXFacebookConfig config) {
		this.config = config;
		preferences = Gdx.app.getPreferences(config.PREF_FILENAME);
	}

	abstract public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback);

	/**
	 * Currently used accessToken. Null if user is not signed in.
	 *
	 * @return accessToken
	 */
	public GDXFacebookAccessToken getAccessToken() {
		return accessToken;
	}


	abstract protected void startGUISignIn();

	protected void startSilentSignIn() {
		if (accessToken != null) {
			Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting silent sign in.");
			GDXFacebookGraphRequest request = new GDXFacebookGraphRequest();
			request.setMethod(Net.HttpMethods.POST);
			request.setNode("");
			request.putField("batch", "[{\"method\":\"GET\", \"relative_url\":\"me\"},{\"method\":\"GET\", \"relative_url\":\"me/permissions\"}]");
			request.putField("include_headers", "false");
			request.useCurrentAccessToken();
			newGraphRequest(request, this);
		} else {
			Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent sign in cancelled. No existing access token.");
		}
	}

	;

	protected void storeNewToken(GDXFacebookAccessToken token) {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Storing new accessToken: " + token.getToken());
		preferences.putString("access_token", token.getToken());
		preferences.putLong("expires_at", token.getExpiresAt());
		preferences.flush();
	}

	protected void loadAccessToken() {
		String token = preferences.getString("access_token", null);
		long expiresAt = preferences.getLong("expires_at", 0);


		if (token != null && expiresAt > 0) {
			Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Loaded existing accessToken: " + token);
			accessToken = new GDXFacebookAccessToken(token, expiresAt);
		} else {
			Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Could not load existing accessToken");
		}
	}

	public void newGraphRequest(GDXFacebookGraphRequest request, final GDXFacebookCallback<JsonResult> callback) {
		String accessToken = null;
		if (getAccessToken() != null) {
			accessToken = getAccessToken().getToken();
		}

		if (request.isUseCurrentAccessToken() && accessToken != null) {
			request.putField("access_token", accessToken);
		}

		Net.HttpRequest httpRequest = new Net.HttpRequest(request.getMethod());
		String url = request.getUrl() + config.GRAPH_API_VERSION + "/" + request.getNode();
		httpRequest.setUrl(url);
		httpRequest.setContent(request.getContentAsString());
		httpRequest.setTimeOut(request.getTimeout());


		HttpRequestBuilder builder = new HttpRequestBuilder().newRequest();
		builder.method(request.getMethod());

		Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

			@Override
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				String resultString = httpResponse.getResultAsString();
				int statusCode = httpResponse.getStatus().getStatusCode();

				if (statusCode == -1) {
					GraphError error = new GraphError("Connection time out. Consider increasing timeout value by using setTimeout()");
					callback.onError(error);
				} else if (statusCode >= 200 && statusCode < 300) {
					callback.onSuccess(new JsonResult(resultString));
				} else {
					GraphError error = new GraphError("Error: " + resultString);
					callback.onError(error);
				}
			}

			@Override
			public void failed(Throwable t) {
				t.printStackTrace();
				callback.onFail(t);
			}

			@Override
			public void cancelled() {
				callback.onCancel();
			}
		});

	}

	private boolean jsonHasCode200AndBody(JsonValue jsonValue) {
		return jsonValue.has("code") && jsonValue.getInt("code") == 200 && jsonValue.has("body");
	}

	@Override
	public void onSuccess(JsonResult result) {
		JsonValue value = result.getJsonValue();
		if (value != null && value.isArray()) {

			JsonValue meValue = value.get(0);
			JsonValue permissionsValue = value.get(1);


			if (jsonHasCode200AndBody(meValue) && jsonHasCode200AndBody(permissionsValue)) {

				JsonReader reader = new JsonReader();
				JsonValue permissionBodyValue = reader.parse(permissionsValue.getString("body"));
				JsonValue permissionArray = permissionBodyValue.get("data");

				Array<String> grantedPermissions = new Array<String>();
				for (int i = 0; i < permissionArray.size; i++) {
					JsonValue permission = permissionArray.get(i);

					if (permission.getString("status").equals("granted")) {
						grantedPermissions.add(permission.getString("permission").toLowerCase());
					}
				}


				if (arePermissionsGranted(grantedPermissions)) {
					Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent sign in successful. Current token is still valid.");
					callback.onSuccess(new SignInResult(accessToken, "Silent sign in successful. Current token is still valid."));
				} else {
					signOut();
					Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Used access_token is valid but new permissions need to be granted. Need GUI sign in.");
					callback.onError(new GraphError("Used access_token is valid but new permissions need to be granted. Need GUI sign in."));
					startGUISignIn();
				}


			} else {
				signOut();
				Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent sign in error: " + value.toString());
				callback.onError(new GraphError(value.toString()));
				startGUISignIn();
			}
		} else {
			callback.onError(new GraphError("Unexpected error occurred while trying to sign in. Error unknown, possible timeout."));
		}
	}

	private boolean arePermissionsGranted(Array<String> grantedPermissions) {
		for (int i = 0; i < permissions.size; i++) {
			if (!grantedPermissions.contains(permissions.get(i).toLowerCase(), false)) {
				return false;
			}
		}
		return true;
	}


	@Override
	public void onError(GraphError error) {
		signOut();
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent sign in error: " + error.getErrorMessage());
		callback.onError(error);
		startGUISignIn();
	}

	@Override
	public void onFail(Throwable t) {
		signOut();
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent sign in failed: " + t);
		callback.onFail(t);
		startGUISignIn();
	}

	@Override
	public void onCancel() {
		signOut();
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent sign in cancelled");
		callback.onCancel();
		startGUISignIn();
	}

	public boolean isSignedIn() {
		return accessToken != null;
	}


	public void signOut() {
		accessToken = null;
	}
}

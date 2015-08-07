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

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

/**
 * 
 * @author Thomas Pronold (TomGrill) mail@tomgrill.de
 *
 */
public abstract class GDXFacebook {

	private boolean waitingForResponse = false;
	private boolean permissionsGranted = false;

	protected Preferences prefs;
	protected GDXFacebookConfig config;

	public GDXFacebook(final GDXFacebookConfig config) {
		this.config = config;

		prefs = Gdx.app.getPreferences(config.PREF_FILENAME);
	}

	/**
	 * This will start a login process. The login process is usually done
	 * silently in the background. However there are two cases when the a
	 * Facebook Login GUI is shown to the user. 1) The user has to grant
	 * permissions. 2) An AccessToken is null, invalid or expired.
	 * 
	 * @param permissions
	 *            List of Strings with requested permissions. Learn more about
	 *            Facebook Permissions: <a href=
	 *            "https://developers.facebook.com/docs/facebook-login/permissions/"
	 *            >https://developers.facebook.com/docs/facebook-login/
	 *            permissions/</a>
	 * 
	 * @param callback
	 *            callback handling the login result
	 */
	abstract public void loginWithReadPermissions(Collection<String> permissions, GDXFacebookCallback<GDXFacebookLoginResult> callback);

	/**
	 * This will start a login process. The login process is usually done
	 * silently in the background. However there are two cases when the a
	 * Facebook Login GUI is shown to the user. 1) The user has to grant
	 * permissions. 2) An AccessToken is null, invalid or expired.
	 * 
	 * @param permissions
	 *            List of Strings with requested permissions. Learn more about
	 *            Facebook Permissions: <a href=
	 *            "https://developers.facebook.com/docs/facebook-login/permissions/"
	 *            >https://developers.facebook.com/docs/facebook-login/
	 *            permissions/</a>
	 * 
	 * @param callback
	 *            callback handling the login result
	 */
	abstract public void loginWithPublishPermissions(Collection<String> permissions, GDXFacebookCallback<GDXFacebookLoginResult> callback);

	/**
	 * Returns whether the user is currently logged in.
	 * 
	 * @return
	 */
	abstract public boolean isLoggedIn();

	/**
	 * Returns the currently used access token. If you have a multiuser Facebook
	 * application retrieve the access tokens with this method and store them
	 * accordingly.
	 * 
	 * @return returns access token or null if no valid access token exists.
	 */
	abstract public GDXFacebookAccessToken getAccessToken();

	// /**
	// * Sets the accessToken. This is useful if you have a multiuser Facebook
	// * application or want to reuse the access token from an earlier Facebook
	// * SDK implementation.
	// *
	// * @return
	// */
	// @Deprecated
	// abstract public void setAccessToken(String accessToken);

	/**
	 * Logs the user out.
	 */
	abstract public void logOut();

	public void newGraphRequest(GDXFacebookGraphRequest request, final GDXFacebookCallback<GDXFacebookGraphResult> callback) {
		String accessToken = null;
		if (getAccessToken() != null) {
			accessToken = getAccessToken().getToken();
		}

		if (request.isUseCurrentAccessToken() && accessToken != null) {
			request.putField("access_token", accessToken);
		}

		HttpRequestBuilder builder = new HttpRequestBuilder().newRequest();

		builder.method(request.getMethod());
		builder.url(request.getUrl());
		builder.content(request.getContentAsString());
		builder.timeout(request.getTimeout());

		HttpRequest httpRequest = builder.build();

		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				String resultString = httpResponse.getResultAsString();
				int statusCode = httpResponse.getStatus().getStatusCode();

				if (statusCode == -1) {
					GDXFacebookError error = new GDXFacebookError();
					error.setErrorMessage("Connection time out after. Handle error or increase timeout with GDXFacebookGraphRequest.setTimeout(); ");
					error.setErrorCode("-1");
					callback.onError(error);
				} else if (statusCode >= 200 && statusCode < 300) {
					final GDXFacebookGraphResult gResult = new GDXFacebookGraphResult();
					gResult.setResultAsJson(resultString);
					callback.onSuccess(gResult);
				} else {
					GDXFacebookError error = new GDXFacebookError();

					JsonValue result = new JsonReader().parse(resultString);

					if (result.has("error")) {
						JsonValue errorNode = result.get("error");
						if (errorNode != null) {

							if (errorNode.has("message")) {
								error.setErrorMessage(errorNode.getString("message"));
							}
							if (errorNode.has("code")) {
								error.setErrorCode(errorNode.getString("code"));
							}
							if (errorNode.has("type")) {
								error.setErrorType(errorNode.getString("type"));
							}
							if (errorNode.has("error_subcode")) {
								error.setErrorSubCode(errorNode.getString("error_subcode"));
							}
							if (errorNode.has("error_user_msg")) {
								error.setErrorUserMessage(errorNode.getString("error_user_msg"));
							}
							if (errorNode.has("error_user_title")) {
								error.setErrorUserTitle(errorNode.getString("error_user_title"));
							}
						}

					} else {
						error.setErrorMessage("Unknown error: Facebook response: \n" + resultString);
					}

					callback.onError(error);
				}

			}

			@Override
			public void failed(Throwable t) {
				callback.onFail(t);
			}

			@Override
			public void cancelled() {
				callback.onCancel();
			}
		});

	}

	protected boolean arePermissionsGranted(final Collection<String> permissions) {

		waitingForResponse = true;
		permissionsGranted = false;

		new Thread(new Runnable() {

			@Override
			public void run() {
				GDXFacebookGraphRequest request = new GDXFacebookGraphRequest().setNode("me/permissions").useCurrentAccessToken();
				newGraphRequest(request, new GDXFacebookCallback<GDXFacebookGraphResult>() {

					@Override
					public void onSuccess(GDXFacebookGraphResult result) {

						JsonValue data = new JsonReader().parse(result.getResultAsJson());
						if (data != null && data.has("data")) {
							JsonValue permissionList = data.get("data");

							Array<String> grantedPermissions = new Array<String>();

							for (int i = 0; i < permissionList.size; i++) {
								JsonValue permission = permissionList.get(i);

								if (permission.has("permission") && permission.has("status") && permission.getString("status").equals("granted")) {
									grantedPermissions.add(permission.getString("permission"));
								}
							}
							ArrayList<String> requiredPermissions = (ArrayList<String>) permissions;

							for (int i = 0; i < requiredPermissions.size(); i++) {
								if (!grantedPermissions.contains(requiredPermissions.get(i), false)) {
									waitingForResponse = false;
									return;
								}
							}
							permissionsGranted = true;
						}
						waitingForResponse = false;
					}

					@Override
					public void onError(GDXFacebookError error) {
						waitingForResponse = false;

					}

					@Override
					public void onFail(Throwable t) {
						waitingForResponse = false;

					}

					@Override
					public void onCancel() {
						waitingForResponse = false;

					}

				});
			}
		}).start();

		while (waitingForResponse) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		return permissionsGranted;
	}

	protected GDXFacebookAccessToken loadAccessToken() {
		String accessTokenAsJson = prefs.getString("accessTokenAsJson", null);
		if (accessTokenAsJson == null) {
			return null;
		}
		// System.out.println("LOAD TOKEN:\n" + accessTokenAsJson);
		Json json = new Json();
		json.setOutputType(OutputType.json);
		return json.fromJson(GDXFacebookAccessToken.class, accessTokenAsJson);
	}

	protected void storeToken(GDXFacebookAccessToken token) {
		if (token == null) {
			prefs.remove("accessTokenAsJson");
		} else {
			Json json = new Json();
			json.setOutputType(OutputType.json);

			// System.out.println("STORE TOKEN:\n" + json.toJson(token));
			prefs.putString("accessTokenAsJson", json.toJson(token));
		}

		prefs.flush();
	}
}

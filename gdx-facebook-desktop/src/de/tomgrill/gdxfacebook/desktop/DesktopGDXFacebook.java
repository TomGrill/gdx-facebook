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

package de.tomgrill.gdxfacebook.desktop;

import java.util.Collection;
import java.util.List;

import javafx.application.Platform;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookAccessToken;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallbackAdapter;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.core.GDXFacebookGraphRequest;
import de.tomgrill.gdxfacebook.core.GDXFacebookGraphResult;
import de.tomgrill.gdxfacebook.core.GDXFacebookLoginResult;

public class DesktopGDXFacebook extends GDXFacebook {

	private GDXFacebookAccessToken accessToken;

	public DesktopGDXFacebook(GDXFacebookConfig config) {
		super(config);
		accessToken = loadAccessToken();
	}

	private void debugAccessToken(GDXFacebookCallback<GDXFacebookGraphResult> callback) {
		GDXFacebookGraphRequest request = new GDXFacebookGraphRequest().setNode("debug_token").useCurrentAccessToken();
		request.putField("input_token", accessToken.getToken());
		newGraphRequest(request, callback);
	}

	private void startGUILogin(final Collection<String> permissions, final GDXFacebookCallback<GDXFacebookLoginResult> callback) {

		if (RunHelper.isStarted) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					JXBrowserDesktopFacebookGUI.reuse();
				}
			});

		} else {
			new Thread(new Runnable() {
				@Override
				public void run() {

					List<String> listPermissions = (List<String>) permissions;
					String permissionString = "";

					for (int i = 0; i < listPermissions.size(); i++) {
						permissionString += listPermissions.get(i);
						if (i + 1 < listPermissions.size()) {
							permissionString += ",";
						}
					}

					JXBrowserDesktopFacebookGUI.setAppId(config.APP_ID);
					JXBrowserDesktopFacebookGUI.setPermissions(permissionString);
					JXBrowserDesktopFacebookGUI.show(callback);

				}
			}).start();
		}

	}

	@Override
	public void loginWithReadPermissions(final Collection<String> permissions, final GDXFacebookCallback<GDXFacebookLoginResult> callback) {

		/**
		 * Prepare a GUI login in case we need it.
		 */
		final GDXFacebookCallback<GDXFacebookLoginResult> desktopCallback = new GDXFacebookCallback<GDXFacebookLoginResult>() {

			@Override
			public void onSuccess(GDXFacebookLoginResult result) {
				/**
				 * GUI login done. Retrieve more info about the user and token.
				 */
				accessToken = result.getAccessToken();
				storeToken(result.getAccessToken());

				debugAccessToken(new GDXFacebookCallbackAdapter<GDXFacebookGraphResult>() {

					@Override
					public void onSuccess(GDXFacebookGraphResult result) {

						String jsonResult = result.getResultAsJson();
						JsonValue data = new JsonReader().parse(jsonResult).get("data");

						if (data.get("is_valid").asBoolean()) {

							String appId = data.get("app_id").asString();
							String userId = data.get("user_id").asString();
							long expiresAt = data.get("expires_at").asLong() * 1000;
							long issuedAt = data.get("issued_at").asLong() * 1000;

							Array<String> scopes = new Array<String>(data.get("scopes").asStringArray());
							Array<String> scopesDeclined = new Array<String>();

							final GDXFacebookLoginResult loginresult = new GDXFacebookLoginResult();

							accessToken = new GDXFacebookAccessToken(getAccessToken().getToken(), appId, userId, scopes, scopesDeclined, expiresAt, issuedAt);
							storeToken(accessToken);

							loginresult.setAccessToken(accessToken);
							callback.onSuccess(loginresult);

						} else {
							logOut();
							GDXFacebookError error = new GDXFacebookError();
							error.setErrorCode("9119");
							error.setErrorMessage("Invalid token after successfull login - this error should never occurr. TODO");
							callback.onError(error);
						}
					}

					@Override
					public void onError(GDXFacebookError error) {
						logOut();
						callback.onError(error);
					}

					@Override
					public void onFail(Throwable t) {
						logOut();
						callback.onFail(t);
					}

					@Override
					public void onCancel() {
						logOut();
						callback.onCancel();
					}

				});

			}

			@Override
			public void onError(GDXFacebookError error) {
				logOut();
				callback.onError(error);
			}

			@Override
			public void onCancel() {
				logOut();
				callback.onCancel();
			}

			@Override
			public void onFail(Throwable t) {
				logOut();
				callback.onFail(t);
			}

		};

		/**
		 * TRY SILENT LOGIN IF POSSIBLE
		 */
		if ((getAccessToken() != null && getAccessToken().getToken().length() > 0) && arePermissionsGranted(permissions)) {

			debugAccessToken(new GDXFacebookCallbackAdapter<GDXFacebookGraphResult>() {

				@Override
				public void onSuccess(GDXFacebookGraphResult result) {

					String jsonResult = result.getResultAsJson();
					JsonValue data = new JsonReader().parse(jsonResult).get("data");

					if (data.get("is_valid").asBoolean()) {

						String appId = data.get("app_id").asString();
						String userId = data.get("user_id").asString();
						long expiresAt = data.get("expires_at").asLong() * 1000;
						long issuedAt = data.get("issued_at").asLong() * 1000;

						Array<String> scopes = new Array<String>(data.get("scopes").asStringArray());
						Array<String> scopesDeclined = new Array<String>();

						final GDXFacebookLoginResult loginresult = new GDXFacebookLoginResult();

						accessToken = new GDXFacebookAccessToken(getAccessToken().getToken(), appId, userId, scopes, scopesDeclined, expiresAt, issuedAt);
						storeToken(accessToken);

						loginresult.setAccessToken(accessToken);
						callback.onSuccess(loginresult);

					} else {
						logOut();
						startGUILogin(permissions, desktopCallback);
					}
				}

				@Override
				public void onError(GDXFacebookError error) {
					logOut();
					startGUILogin(permissions, desktopCallback);
				}

				@Override
				public void onFail(Throwable t) {
					logOut();
					callback.onFail(t);
				}

				@Override
				public void onCancel() {
					logOut();
					callback.onCancel();
				}

			});

		} else {

			startGUILogin(permissions, desktopCallback);
		}

	}

	@Override
	public void loginWithPublishPermissions(Collection<String> permissions, GDXFacebookCallback<GDXFacebookLoginResult> callback) {
		loginWithReadPermissions(permissions, callback);
	}

	@Override
	public boolean isLoggedIn() {
		return accessToken != null;
	}

	@Override
	public void logOut() {
		accessToken = null;
		storeToken(accessToken);
	}

	@Override
	public GDXFacebookAccessToken getAccessToken() {
		return accessToken;
	}

}

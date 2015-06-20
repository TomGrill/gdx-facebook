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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Preferences;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.core.GDXFacebookLoginResult;

public class DesktopGDXFacebook extends GDXFacebook {

	private GDXFacebookConfig config;

	private Preferences prefs;

	private String accessToken;

	private boolean isLoggedIn = false;

	public DesktopGDXFacebook(GDXFacebookConfig config) {
		this.config = config;

		prefs = Gdx.app.getPreferences(config.PREF_FILENAME == null ? "gdx-facebook-desktop.session" : config.PREF_FILENAME);

		String existingToken = prefs.getString("access_token", null);

		if (existingToken != null && existingToken.length() > 0) {
			setAccessToken(existingToken);
		}
	}

	private void verifiyAccessToken(HttpResponseListener listener) {
		newGraphRequest("https://graph.facebook.com/me", null, listener);

	}

	private void startGUILogin(final Collection<String> permissions, final GDXFacebookCallback<GDXFacebookLoginResult> responseListener) {

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
					JXBrowserDesktopFacebookGUI.show(responseListener);

				}
			}).start();
		}

	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		if (accessToken == null) {
			prefs.remove("access_token");
		} else {
			prefs.putString("access_token", accessToken);
		}
		prefs.flush();
	}

	@Override
	public void loginWithReadPermissions(final Collection<String> permissions, final GDXFacebookCallback<GDXFacebookLoginResult> callback) {
		isLoggedIn = false;
		final GDXFacebookCallback<GDXFacebookLoginResult> desktopCallback = new GDXFacebookCallback<GDXFacebookLoginResult>() {

			@Override
			public void onSuccess(GDXFacebookLoginResult result) {
				setAccessToken(result.getAccessToken());
				isLoggedIn = true;
				callback.onSuccess(result);
			}

			@Override
			public void onError(GDXFacebookError error) {
				logOut();
				callback.onError(error);
			}

			@Override
			public void onCancel() {
				callback.onCancel();
			}

		};

		if ((getAccessToken() != null && getAccessToken().length() > 0)) {
			verifiyAccessToken(new HttpResponseListener() {

				@Override
				public void handleHttpResponse(HttpResponse httpResponse) {
					if (httpResponse.getStatus().getStatusCode() == 200) {
						GDXFacebookLoginResult result = new GDXFacebookLoginResult();
						result.setAccessToken(getAccessToken());
						isLoggedIn = true;
						callback.onSuccess(result);

					} else {
						startGUILogin(permissions, desktopCallback);
					}
				}

				@Override
				public void failed(Throwable t) {
					GDXFacebookError error = new GDXFacebookError();
					error.setErrorMessage(t.getMessage());
					logOut();
					callback.onError(error);
				}

				@Override
				public void cancelled() {
					callback.onCancel();
				}
			});
		} else {
			startGUILogin(permissions, desktopCallback);
		}

	}

	@Override
	public void loginWithPublishPermissions(Collection<String> permissions, GDXFacebookCallback<GDXFacebookLoginResult> callback) {

	}

	@Override
	public boolean isLoggedIn() {
		if (isLoggedIn && getAccessToken() != null) {
			return true;
		}
		return false;
	}

	@Override
	public void logOut() {
		isLoggedIn = false;
	}

	@Override
	public String getAccessToken() {
		return accessToken;
	}

}

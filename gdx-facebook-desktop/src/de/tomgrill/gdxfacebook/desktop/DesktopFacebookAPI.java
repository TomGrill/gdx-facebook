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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Preferences;

import de.tomgrill.gdxfacebook.core.FacebookAPI;
import de.tomgrill.gdxfacebook.core.FacebookConfig;
import de.tomgrill.gdxfacebook.core.FacebookLoginListener;
import de.tomgrill.gdxfacebook.core.ResponseError;
import de.tomgrill.gdxfacebook.core.ResponseListener;

public class DesktopFacebookAPI extends FacebookAPI {

	private FacebookConfig config;

	private Preferences prefs;

	public DesktopFacebookAPI(FacebookConfig config) {
		this.config = config;

		prefs = Gdx.app.getPreferences(config.PREF_FILENAME);

		String existingToken = prefs.getString("access_token", null);

		if (existingToken != null && existingToken.length() > 0) {
			setAccessToken(existingToken);
		}
	}

	private void verifiyAccessToken(String accessToken, HttpResponseListener listener) {
		newGraphRequest("https://graph.facebook.com/me", "access_token=" + accessToken, listener);

	}

	@Override
	public void signin(final boolean allowGUI, final ResponseListener responseListener) {
		signout();

		if (allowGUI) {

			// try silent first
			if ((accessToken != null && accessToken.length() > 0)) {
				verifiyAccessToken(accessToken, new HttpResponseListener() {

					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
						if (httpResponse.getStatus().getStatusCode() == 200) {
							isSignedin = true;
							responseListener.success();
						} else {
							startGUILogin(responseListener);
						}
					}

					@Override
					public void failed(Throwable t) {
						ResponseError error = new ResponseError();
						error.setCode(ResponseError.EC_FAILED);
						error.setMessage("Cannot restore session. FAILED " + t.getMessage());
						responseListener.error(error);
					}

					@Override
					public void cancelled() {
						ResponseError error = new ResponseError();
						error.setCode(ResponseError.EC_CANCELED);
						error.setMessage("Cannot restore Facebook session. CANCELED");
						responseListener.error(error);
					}
				});
			} else {
				startGUILogin(responseListener);
			}

		} else {

			if ((accessToken == null || accessToken.length() == 0)) {
				ResponseError error = new ResponseError();
				error.setCode(ResponseError.EC_EMPTY_ACCESS_TOKEN);
				error.setMessage("Cannot restore session. No acces token given. Use setAccessToken() before when performing silent login.");
				responseListener.error(error);
			} else {

				verifiyAccessToken(accessToken, new HttpResponseListener() {

					@Override
					public void handleHttpResponse(HttpResponse httpResponse) {
						if (httpResponse.getStatus().getStatusCode() == 200) {
							isSignedin = true;
							responseListener.success();
						} else {
							ResponseError error = new ResponseError();
							error.setCode(ResponseError.EC_BAD_REQUEST);
							error.setMessage("Cannot restore session. BAD REQUEST");
							responseListener.error(error);
						}
					}

					@Override
					public void failed(Throwable t) {
						ResponseError error = new ResponseError();
						error.setCode(ResponseError.EC_FAILED);
						error.setMessage("Cannot restore session. FAILED " + t.getMessage());
						responseListener.error(error);
					}

					@Override
					public void cancelled() {
						ResponseError error = new ResponseError();
						error.setCode(ResponseError.EC_CANCELED);
						error.setMessage("Cannot restore Facebook session. CANCELED");
						responseListener.error(error);
					}
				});
			}
		}

	}

	private void startGUILogin(final ResponseListener responseListener) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				JXBrowserDesktopFacebookGUI browser = new JXBrowserDesktopFacebookGUI();
				browser.setAppId(config.APP_ID);
				browser.setPermissions(config.PERMISSIONS);
				browser.show(new FacebookLoginListener() {

					@Override
					public void onSuccess(String accessToken, long expires) {
						isSignedin = true;
						setAccessToken(accessToken);
						responseListener.success();
					}

					@Override
					public void onError(String error, String errorCode, String errorDescription, String errorReason) {
						ResponseError listenerError = new ResponseError();
						listenerError.setCode(ResponseError.EC_FAILED);
						// TODO this error codes may be different... Proof. Not
						// sure
						// what FB is capeable of returning in ResponderBody
						listenerError.setMessage("GUI Login failed. \n Error: " + error + "\nError Code;" + errorCode + "\nError Description;" + errorDescription
								+ "\nError Reason;" + errorReason);
						responseListener.error(listenerError);
					}

					@Override
					public void onCancel() {
						responseListener.cancel();

					}
				});

			}
		}).start();

	}

	@Override
	public void setAccessToken(String accessToken) {
		super.setAccessToken(accessToken);
		prefs.putString("access_token", accessToken);
		prefs.flush();
	}

}

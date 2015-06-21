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

import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * 
 * @author Thomas Pronold (TomGrill) mail@tomgrill.de
 *
 */
public abstract class GDXFacebook {

	private GDXFacebookCallback<GDXFacebookGraphResult> callback;

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

	abstract public String getAccessToken();

	/**
	 * Logs the user out.
	 */
	abstract public void logOut();

	final public void newGraphRequest(GDXFacebookGraphRequest request, final GDXFacebookCallback<GDXFacebookGraphResult> callback) {

		if (request.isUseCurrentAccessToken()) {
			request.putField("access_token", getAccessToken());
		}

		String url = request.build();

		HttpRequest httpRequest = new HttpRequestBuilder().newRequest().method(HttpMethods.GET).url(url).build();
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				String resultString = httpResponse.getResultAsString();

				if (httpResponse.getStatus().getStatusCode() >= 200 && httpResponse.getStatus().getStatusCode() < 300) {
					final GDXFacebookGraphResult gResult = new GDXFacebookGraphResult();
					gResult.setResultAsJson(resultString);
					callback.onSuccess(gResult);

				} else {
					GDXFacebookError error = new GDXFacebookError();

					JsonValue errorNode = new JsonReader().parse(resultString).get("error");
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
}

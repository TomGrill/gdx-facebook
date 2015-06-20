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
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;

public abstract class GDXFacebook {

	/**
	 * This will start a login process. The login process is usually done
	 * silently in the background. However there are two cases when the a
	 * Facebook Login GUI is shown to the user. 1) The user has to grant
	 * permissions. 2) An AccessToken is null, invalid or expired.
	 * 
	 * @param permissions
	 *            List of Strings with requested permissions. Learn more about
	 *            Facebook Permissions: @see <a href=
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
	 *            Facebook Permissions: @see <a href=
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

	final public void newGraphRequest(String path, String parameter, final HttpResponseListener listener) {
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		String uri = "";

		if (path != null) {
			uri += path;
		} else {
			uri = "https://graph.facebook.com/";
		}
		uri += "?";

		if (parameter != null) {
			uri += parameter;
		}

		if (getAccessToken() != null) {
			uri += "&access_token=" + getAccessToken();
		}

		System.out.println(uri);

		HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url(uri).build();

		Gdx.net.sendHttpRequest(httpRequest, listener);
	}
}

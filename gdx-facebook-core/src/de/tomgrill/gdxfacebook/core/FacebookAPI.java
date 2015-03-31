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
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;

public abstract class FacebookAPI {

	protected boolean isSignedin;

	protected String accessToken;

	final public void signin(ResponseListener responseListener) {
		signin(true, responseListener);
	}

	/**
	 * Provide a way to login with facebook. The method must make use of
	 * setAccessToken() after a successfull login.
	 * 
	 * 
	 */
	abstract public void signin(boolean allowGUI, ResponseListener responseListener);

	public boolean isSignedin() {
		return isSignedin;
	}

	public void signout() {
		isSignedin = false;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	final public void newGraphRequest(String path, String parameter, final HttpResponseListener listner) {
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();

		String uri = "https://graph.facebook.com/" + path + "?" + parameter;
		HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url(uri).build();

		Gdx.net.sendHttpRequest(httpRequest, listner);

	}

}

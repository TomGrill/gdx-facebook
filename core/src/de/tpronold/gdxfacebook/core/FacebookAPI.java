package de.tpronold.gdxfacebook.core;

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
	 * @param responseListener
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

		String uri = path + "?" + parameter;
		HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url(uri).build();

		Gdx.net.sendHttpRequest(httpRequest, listner);

	}

}

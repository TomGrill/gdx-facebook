package de.tpronold.gdxfacebook.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;

public class FacebookGraphRequest {

	private FacebookGraphRequestType type;

	private JSONRequestListener listener;

	private String accessToken;

	private final String graphURI = "https://graph.facebook.com/";

	public FacebookGraphRequest(FacebookGraphRequestType type, String accessToken, JSONRequestListener listener) {
		this.type = type;
		this.listener = listener;
		this.accessToken = accessToken;
	}

	public void sendRequest() {
		sendRequest(true);

	}

	public void sendRequest(boolean background) {
		if (background) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					doRequest();
				}

			}).start();
		} else {
			doRequest();
		}
	}

	private void doRequest() {
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();

		String uri = graphURI + type.toString().toLowerCase() + "?access_token=" + accessToken;
		HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.POST).url(uri).build();

		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				listener.requestResult(httpResponse.getResultAsString());
			}

			@Override
			public void failed(Throwable t) {

			}

			@Override
			public void cancelled() {

			}
		});

	}
}

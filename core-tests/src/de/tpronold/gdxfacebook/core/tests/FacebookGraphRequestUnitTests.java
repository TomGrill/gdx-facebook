package de.tpronold.gdxfacebook.core.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpStatus;

import de.tpronold.gdxfacebook.core.FacebookGraphRequest;
import de.tpronold.gdxfacebook.core.FacebookGraphRequestType;
import de.tpronold.gdxfacebook.core.JSONRequestListener;

public class FacebookGraphRequestUnitTests {

	private FacebookGraphRequest fixture;

	private static final String invalidTokenErrorMeRequest = "{\"error\":{\"message\":\"Invalid OAuth access token.\",\"type\":\"OAuthException\",\"code\":190}}";
	private static final String validMeRequest = "{\"id\":\"444444444444\",\"email\":\"tom\u0040test.de\",\"first_name\":\"Tom\",\"gender\":\"male\",\"last_name\":\"Grill\",\"link\":\"https://www.facebook.com/app_scoped_user_id/443244444/\",\"locale\":\"de_DE\",\"name\":\"Tom Grill\",\"timezone\":1,\"updated_time\":\"2012-07-03T18:55:17+0000\",\"verified\":true}";

	private String resultForVerifyTokenFailsWhenWrong;
	private String resultForVerifyTokenFailsWhenWrongAsBackgroundThread;

	@Before
	public void setup() {
		Gdx.net = mock(Net.class);
	}

	private void makeSendHttpRequestFail() {
		Mockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {

				Object[] args = invocation.getArguments();

				HttpResponseListener listener = (HttpResponseListener) args[1];

				listener.handleHttpResponse(new HttpResponse() {

					@Override
					public HttpStatus getStatus() {
						return null;
					}

					@Override
					public String getResultAsString() {
						return invalidTokenErrorMeRequest;
					}

					@Override
					public InputStream getResultAsStream() {
						return null;
					}

					@Override
					public byte[] getResult() {
						return null;
					}

					@Override
					public Map<String, List<String>> getHeaders() {
						return null;
					}

					@Override
					public String getHeader(String name) {
						return null;
					}
				});

				return null;
			}
		}).when(Gdx.net).sendHttpRequest(any(HttpRequest.class), any(HttpResponseListener.class));
	}

	private void makeSendHttpRequestSucceed() {
		Mockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {

				Object[] args = invocation.getArguments();

				HttpResponseListener listener = (HttpResponseListener) args[1];

				listener.handleHttpResponse(new HttpResponse() {

					@Override
					public HttpStatus getStatus() {
						return null;
					}

					@Override
					public String getResultAsString() {
						return validMeRequest;
					}

					@Override
					public InputStream getResultAsStream() {
						return null;
					}

					@Override
					public byte[] getResult() {
						return null;
					}

					@Override
					public Map<String, List<String>> getHeaders() {
						return null;
					}

					@Override
					public String getHeader(String name) {
						return null;
					}
				});

				return null;
			}
		}).when(Gdx.net).sendHttpRequest(any(HttpRequest.class), any(HttpResponseListener.class));
	}

	@Test
	public void verifyTokenFailsWhenWrong() {
		makeSendHttpRequestFail();
		fixture = new FacebookGraphRequest(FacebookGraphRequestType.ME, "", new JSONRequestListener() {
			@Override
			public void requestResult(String jsonBody) {
				resultForVerifyTokenFailsWhenWrong = jsonBody;
			}
		});

		fixture.sendRequest(false);
		while (resultForVerifyTokenFailsWhenWrong == null) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		assertEquals(invalidTokenErrorMeRequest, resultForVerifyTokenFailsWhenWrong);
	}

	@Test
	public void verifyTokenFailsWhenWrongAsBackgroundThread() {
		makeSendHttpRequestFail();
		fixture = new FacebookGraphRequest(FacebookGraphRequestType.ME, "", new JSONRequestListener() {
			@Override
			public void requestResult(String jsonBody) {
				resultForVerifyTokenFailsWhenWrongAsBackgroundThread = jsonBody;
			}
		});

		fixture.sendRequest();
		while (resultForVerifyTokenFailsWhenWrongAsBackgroundThread == null) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		assertEquals(invalidTokenErrorMeRequest, resultForVerifyTokenFailsWhenWrongAsBackgroundThread);
	}

}

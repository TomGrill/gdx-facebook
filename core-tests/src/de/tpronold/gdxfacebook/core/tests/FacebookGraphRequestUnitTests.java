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

	private static final String invalidTokenError = "{\"error\":{\"message\":\"Invalid OAuth access token.\",\"type\":\"OAuthException\",\"code\":190}}";

	private String resultForVerifyTokenFailsWhenWrong;
	private String resultForVerifyTokenFailsWhenWrongAsBackgroundThread;

	@Before
	public void setup() {

		// HeadlessApplicationConfiguration conf = new
		// HeadlessApplicationConfiguration();
		// new HeadlessApplication(new ApplicationAdapter() {
		// }, conf);

		Gdx.net = mock(Net.class);

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
						return invalidTokenError;
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
		assertEquals(invalidTokenError, resultForVerifyTokenFailsWhenWrong);
	}

	@Test
	public void verifyTokenFailsWhenWrongAsBackgroundThread() {
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
		assertEquals(invalidTokenError, resultForVerifyTokenFailsWhenWrongAsBackgroundThread);
	}

}

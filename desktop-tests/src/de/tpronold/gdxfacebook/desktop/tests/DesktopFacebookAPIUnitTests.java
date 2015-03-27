package de.tpronold.gdxfacebook.desktop.tests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.net.HttpStatus;

import de.tpronold.gdxfacebook.core.FacebookConfig;
import de.tpronold.gdxfacebook.core.ResponseError;
import de.tpronold.gdxfacebook.core.ResponseListener;
import de.tpronold.gdxfacebook.desktop.DesktopFacebookAPI;

public class DesktopFacebookAPIUnitTests {

	private DesktopFacebookAPI fixture;

	private FacebookConfig config;

	@Before
	public void setup() {

		config = new FacebookConfig();
		fixture = new DesktopFacebookAPI(config);

		HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
		new HeadlessApplication(new ApplicationAdapter() {
		}, conf);

		Gdx.app = mock(HeadlessApplication.class);
		Gdx.net = mock(Net.class);

		Preferences prefs = Mockito.mock(Preferences.class);

		when(Gdx.app.getPreferences(anyString())).thenReturn(prefs);
		when(prefs.getString("accessToken", "")).thenReturn("INVALID_TOKEN");

	}

	@Test
	public void verifySigninDoesSignout() {
		DesktopFacebookAPI spy = Mockito.spy(fixture);
		ResponseListenerStub listener = new ResponseListenerStub();
		spy.signin(listener);
		verify(spy, times(1)).signout();
	}

	@Test
	public void errorCode_EC_EMPTY_ACCESS_TOKEN_whenNoAccessTokenAndSilentLogin() {
		ResponseListener listener = mock(ResponseListener.class);
		fixture.signin(false, listener);
		verify(listener, times(1)).error(argThat(new ArgumentMatcher<ResponseError>() {
			@Override
			public boolean matches(Object argument) {
				ResponseError error = (ResponseError) argument;
				if (error.getCode() == ResponseError.EC_EMPTY_ACCESS_TOKEN) {
					return true;
				}
				return false;
			}
		}));

	}

	@Test
	public void errorCode_EC_CANCELED_noGUIAllowed() {

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HttpResponseListener rListener = (HttpResponseListener) args[1];
				rListener.cancelled();
				return null;
			}
		}).when(Gdx.net).sendHttpRequest(any(HttpRequest.class), any(HttpResponseListener.class));

		ResponseListener listener = mock(ResponseListener.class);

		fixture.setAccessToken("fdgsdfgsdgsdf");
		fixture.signin(false, listener);
		verify(listener, times(1)).error(argThat(new ArgumentMatcher<ResponseError>() {
			@Override
			public boolean matches(Object argument) {
				ResponseError error = (ResponseError) argument;
				if (error.getCode() == ResponseError.EC_CANCELED) {
					return true;
				}
				return false;
			}
		}));

	}

	@Test
	public void errorCode_EC_CANCELED_withGUI() {

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HttpResponseListener rListener = (HttpResponseListener) args[1];
				rListener.cancelled();
				return null;
			}
		}).when(Gdx.net).sendHttpRequest(any(HttpRequest.class), any(HttpResponseListener.class));

		ResponseListener listener = mock(ResponseListener.class);

		fixture.setAccessToken("fdgsdfgsdgsdf");
		fixture.signin(listener);
		verify(listener, times(1)).error(argThat(new ArgumentMatcher<ResponseError>() {
			@Override
			public boolean matches(Object argument) {
				ResponseError error = (ResponseError) argument;
				if (error.getCode() == ResponseError.EC_CANCELED) {
					return true;
				}
				return false;
			}
		}));

	}

	@Test
	public void errorCode_EC_FAILED_noGUIAllowed() {

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HttpResponseListener rListener = (HttpResponseListener) args[1];
				rListener.failed(new Throwable());
				return null;
			}
		}).when(Gdx.net).sendHttpRequest(any(HttpRequest.class), any(HttpResponseListener.class));

		ResponseListener listener = mock(ResponseListener.class);
		fixture.setAccessToken("sdfgsdfgsdg");
		fixture.signin(false, listener);
		verify(listener, times(1)).error(argThat(new ArgumentMatcher<ResponseError>() {
			@Override
			public boolean matches(Object argument) {
				ResponseError error = (ResponseError) argument;
				if (error.getCode() == ResponseError.EC_FAILED) {
					return true;
				}
				return false;
			}
		}));

	}

	@Test
	public void errorCode_EC_FAILED_withGui() {

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HttpResponseListener rListener = (HttpResponseListener) args[1];
				rListener.failed(new Throwable());
				return null;
			}
		}).when(Gdx.net).sendHttpRequest(any(HttpRequest.class), any(HttpResponseListener.class));

		ResponseListener listener = mock(ResponseListener.class);
		fixture.setAccessToken("sdfgsdfgsdg");
		fixture.signin(listener);
		verify(listener, times(1)).error(argThat(new ArgumentMatcher<ResponseError>() {
			@Override
			public boolean matches(Object argument) {
				ResponseError error = (ResponseError) argument;
				if (error.getCode() == ResponseError.EC_FAILED) {
					return true;
				}
				return false;
			}
		}));

	}

	@Test
	public void errorCode_EC_BAD_REQUEST_noGUIAllowed() {

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HttpResponseListener rListener = (HttpResponseListener) args[1];
				rListener.handleHttpResponse(new HttpResponse() {

					@Override
					public HttpStatus getStatus() {
						return new HttpStatus(401230); // Not 200
					}

					@Override
					public String getResultAsString() {
						return null;
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

		ResponseListener listener = mock(ResponseListener.class);
		fixture.setAccessToken("gsdfgsdfgsdf");

		fixture.signin(false, listener);
		verify(listener, times(1)).error(argThat(new ArgumentMatcher<ResponseError>() {
			@Override
			public boolean matches(Object argument) {
				ResponseError error = (ResponseError) argument;
				if (error.getCode() == ResponseError.EC_BAD_REQUEST) {
					return true;
				}
				return false;
			}
		}));

	}

	@Test
	public void errorCode_EC_BAD_REQUEST_withGUI_processesToGUILoginWithBrowser_AND_succeeds() {

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HttpResponseListener rListener = (HttpResponseListener) args[1];
				rListener.handleHttpResponse(new HttpResponse() {

					@Override
					public HttpStatus getStatus() {
						return new HttpStatus(200); // Not 200
					}

					@Override
					public String getResultAsString() {
						return null;
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

		ResponseListener listener = mock(ResponseListener.class);
		fixture.setAccessToken("gsdfgsdfgsdf");

		fixture.signin(false, listener);
		verify(listener, times(1)).success();

	}

	@Test
	public void errorCode_EC_OK_noGUIAllowed() {

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HttpResponseListener rListener = (HttpResponseListener) args[1];
				rListener.handleHttpResponse(new HttpResponse() {

					@Override
					public HttpStatus getStatus() {
						return new HttpStatus(200);
					}

					@Override
					public String getResultAsString() {
						return null;
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

		ResponseListener listener = mock(ResponseListener.class);
		fixture.setAccessToken("hgdfdfsd");
		fixture.signin(false, listener);
		verify(listener, times(1)).success();

	}

	@Test
	public void errorCode_EC_OK_withGUI() {

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HttpResponseListener rListener = (HttpResponseListener) args[1];
				rListener.handleHttpResponse(new HttpResponse() {

					@Override
					public HttpStatus getStatus() {
						return new HttpStatus(200);
					}

					@Override
					public String getResultAsString() {
						return null;
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

		ResponseListener listener = mock(ResponseListener.class);
		fixture.setAccessToken("sdfgsdfg");
		fixture.signin(listener);
		verify(listener, times(1)).success();

	}
}

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


package de.tomgrill.gdxfacebook.tests.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallbackAdapter;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.core.JsonResult;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.core.SignInResult;
import de.tomgrill.gdxfacebook.desktop.DesktopGDXFacebook;
import de.tomgrill.gdxfacebook.desktop.JXBrowser;
import de.tomgrill.gdxfacebook.desktop.JXBrowserCallbackHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JXBrowser.class})
public class DesktopGDXFacebookUnitTests {

	private static String ACCESS_TOKEN = "CAAWKPADhZD";
	private static long EXPIRES_IN = 5184000L;

	private static String ERROR_URL = "https://www.facebook.com/connect/login_success.html?error=access_denied&error_code=200&error_description=Permissions+error&error_reason=user_denied#_=_";
	private static String SUCCESS_URL = "https://www.facebook.com/connect/login_success.html#access_token=" + ACCESS_TOKEN + "&expires_in=" + EXPIRES_IN;

	private boolean testDidSucceed_1;
	private boolean testDidSucceed_2;

	DesktopGDXFacebook fixture;

	Array<String> permissions;
	GDXFacebookCallback mockCallback;
	GDXFacebookConfig config;
	JXBrowserCallbackHandler mockCallbackHandler;
	Preferences mockPreferences;
	Application mockApplication;
	Net mockNet;

	@Before
	public void setup() {
		config = new GDXFacebookConfig();

		Array<String> permissions = new Array<String>();
		permissions.add("public_profile");
		permissions.add("user_friends");
		permissions.add("email");

		mockCallback = mock(GDXFacebookCallback.class);
		mockCallbackHandler = mock(JXBrowserCallbackHandler.class);

		mockStatic(JXBrowser.class);

		mockApplication = mock(Application.class);
		Gdx.app = mockApplication;

		mockPreferences = mock(Preferences.class);

		when(mockApplication.getPreferences(anyString())).thenReturn(mockPreferences);

		mockNet = mock(Net.class);
		Gdx.net = mockNet;

		fixture = new DesktopGDXFacebook(config);
	}



	@Test
	public void signInAndCancel() {
		PowerMockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				fixture.handleCancel();
				return null;
			}
		}).when(JXBrowser.class);
		JXBrowser.login(permissions, config, fixture);

		fixture.signIn(SignInMode.READ, permissions, mockCallback);

		verify(mockCallback).onCancel();
	}

	@Test
	public void signInAndError() {
		PowerMockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				fixture.handleURL(ERROR_URL);
				return null;
			}
		}).when(JXBrowser.class);
		JXBrowser.login(permissions, config, fixture);

		fixture.signIn(SignInMode.READ, permissions, mockCallback);

		verify(mockCallback).onError(any(GDXFacebookError.class));
	}


	@Test
	public void signInAndErrorWithMessage() {
		testDidSucceed_1 = false;

		GDXFacebookCallback callback = new GDXFacebookCallbackAdapter() {
			@Override
			public void onError(GDXFacebookError error) {
				if (error.getErrorMessage().equals("error=access_denied&error_code=200&error_description=Permissions+error&error_reason=user_denied")) {
					testDidSucceed_1 = true;
				}
			}
		};

		PowerMockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				fixture.handleURL(ERROR_URL);
				return null;
			}
		}).when(JXBrowser.class);
		JXBrowser.login(permissions, config, fixture);

		fixture.signIn(SignInMode.READ, permissions, callback);

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue(testDidSucceed_1);
	}

	@Test
	public void signInAndSuccess() {
		PowerMockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				fixture.handleURL(SUCCESS_URL);
				return null;
			}
		}).when(JXBrowser.class);
		JXBrowser.login(permissions, config, fixture);

		fixture.signIn(SignInMode.READ, permissions, mockCallback);

		verify(mockCallback).onSuccess(any(JsonResult.class));
	}

	@Test
	public void signInAndSuccessWithGraphResultAndTokenIsStored() {

		testDidSucceed_2 = false;

		GDXFacebookCallback callback = new GDXFacebookCallbackAdapter<SignInResult>() {
			@Override
			public void onSuccess(SignInResult result) {
				testDidSucceed_2 = true;
				assertEquals(ACCESS_TOKEN, result.getAccessToken().getToken());
				assertEquals(EXPIRES_IN + TimeUtils.millis() / 1000L, result.getAccessToken().getExpiresAt());

				verify(mockPreferences).putString("access_token", ACCESS_TOKEN);
				verify(mockPreferences).putLong(eq("expires_at"), anyInt());
				verify(mockPreferences).flush();
			}

		};

		PowerMockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				fixture.handleURL(SUCCESS_URL);
				return null;
			}
		}).when(JXBrowser.class);
		JXBrowser.login(permissions, config, fixture);

		fixture.signIn(SignInMode.READ, permissions, callback);

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue(testDidSucceed_2);
	}


	@Test
	public void createNewDesktopGDXFacebookInstanceSetsAccessTokenNullWhenCannotLoad() {
		when(mockPreferences.getString("access_token", null)).thenReturn(null);
		when(mockPreferences.getLong("expires_at", 0)).thenReturn(0L);
		fixture = new DesktopGDXFacebook(config);
		assertNull(fixture.getAccessToken());
	}


	@Test
	public void silentLogin() {
		GDXFacebookCallback callback = new GDXFacebookCallbackAdapter();

		fixture.signIn(SignInMode.READ, permissions, callback);
	}
}
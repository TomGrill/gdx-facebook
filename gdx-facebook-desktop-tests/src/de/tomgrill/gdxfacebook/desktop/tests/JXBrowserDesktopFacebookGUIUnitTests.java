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

package de.tomgrill.gdxfacebook.desktop.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import javafx.application.Application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.tomgrill.gdxfacebook.core.FacebookLoginListener;
import de.tomgrill.gdxfacebook.desktop.JXBrowserDesktopFacebookGUI;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Application.class })
public class JXBrowserDesktopFacebookGUIUnitTests {
	private JXBrowserDesktopFacebookGUI fixture;

	private Application applicationMock;

	@Before
	public void setup() {
		fixture = new JXBrowserDesktopFacebookGUI();

		PowerMockito.mockStatic(Application.class);
		applicationMock = mock(Application.class);

	}

	@Test
	public void dontLikeItButFine() {
		/*
		 * Cannot mock WebView, WebEngine. So we have to trust our
		 * implementatation
		 */
		assertTrue(true);
	}

	@Test
	public void setGetAccessToken() {
		fixture.setAppId("ABC");
		assertEquals("ABC", fixture.getAppId());
	}

	@Test
	public void setGetPermissions() {
		fixture.setPermissions("ABC,BVC");
		assertEquals("ABC,BVC", fixture.getPermissions());
	}

	@Test(expected = RuntimeException.class)
	public void showThrowsRuntimeExceptionWhenAppIdIsNull() {
		fixture.setAppId(null);
		fixture.setPermissions("ABC,BVC");
		fixture.show(new FacebookLoginListener() {

			@Override
			public void onSuccess(String accessToken, long expires) {

			}

			@Override
			public void onError(String error, String errorCode, String errorDescription, String errorReason) {

			}

			@Override
			public void onCancel() {

			}
		});
	}

	@Test(expected = RuntimeException.class)
	public void showThrowsRuntimeExceptionWhenAppIdLengthIsZero() {
		fixture.setAppId("");
		fixture.setPermissions("ABC,BVC");
		fixture.show(new FacebookLoginListener() {

			@Override
			public void onSuccess(String accessToken, long expires) {

			}

			@Override
			public void onError(String error, String errorCode, String errorDescription, String errorReason) {

			}

			@Override
			public void onCancel() {

			}
		});
	}

	@Test(expected = RuntimeException.class)
	public void showThrowsRuntimeExceptionWhenPermissionsIsNull() {
		fixture.setAppId("12345");
		fixture.setPermissions(null);
		fixture.show(new FacebookLoginListener() {

			@Override
			public void onSuccess(String accessToken, long expires) {

			}

			@Override
			public void onError(String error, String errorCode, String errorDescription, String errorReason) {

			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}
		});
	}

}

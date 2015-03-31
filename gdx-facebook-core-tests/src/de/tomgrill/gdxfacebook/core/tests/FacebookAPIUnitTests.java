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

package de.tomgrill.gdxfacebook.core.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.tomgrill.gdxfacebook.core.FacebookAPI;
import de.tomgrill.gdxfacebook.core.FacebookConfig;

public class FacebookAPIUnitTests {

	private FacebookAPI fixture;

	private FacebookConfig config;

	@Before
	public void setup() {

		config = new FacebookConfig();

		fixture = mock(FacebookAPI.class, CALLS_REAL_METHODS);
	}

	@Ignore
	@Test
	public void verifySignoutSetsIsSignedinBoolean() {
		assertTrue(fixture.isSignedin());
		fixture.signout();
		assertFalse(fixture.isSignedin());
	}
}

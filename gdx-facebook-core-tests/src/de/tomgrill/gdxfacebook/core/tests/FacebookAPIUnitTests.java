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

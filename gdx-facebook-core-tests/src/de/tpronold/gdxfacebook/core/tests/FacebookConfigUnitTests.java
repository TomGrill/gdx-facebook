package de.tpronold.gdxfacebook.core.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tpronold.gdxfacebook.core.FacebookConfig;

public class FacebookConfigUnitTests {

	FacebookConfig fixture;

	@Before
	public void setup() {
		fixture = new FacebookConfig();
	}

	@Test
	public void defaultValues() {
		assertEquals(false, fixture.ENABLE_ANDROID);
		assertEquals(false, fixture.ENABLE_DESKTOP);
		assertEquals(false, fixture.ENABLE_HTML);
		assertEquals(false, fixture.ENABLE_IOS);
		assertEquals("libgdx-facebook.pref", fixture.PREF_FILENAME);
		assertEquals("", fixture.APP_ID);
		assertEquals("email,public_profile,user_friends", fixture.PERMISSIONS);
	}
}

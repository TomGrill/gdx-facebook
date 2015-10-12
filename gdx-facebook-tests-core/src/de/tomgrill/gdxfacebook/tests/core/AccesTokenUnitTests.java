package de.tomgrill.gdxfacebook.tests.core;

import org.junit.Before;
import org.junit.Test;

import de.tomgrill.gdxfacebook.core.AccessToken;

public class AccesTokenUnitTests {
    AccessToken fixture;

    @Before
    public void setup() {
        fixture = new AccessToken("ABC", 123);
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenConstructorTokenIsNull() {
        new AccessToken(null, 123);
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenSetTokenIsNull() {
        fixture.setToken(null);
    }

}

package de.tomgrill.gdxfacebook.tests.core;

import org.junit.Before;
import org.junit.Test;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.tests.core.stubs.GDXFacebookStub;

import static org.mockito.Mockito.mock;

public class GDXFacebookUnitTests {
    GDXFacebook fixture;

    @Before
    public void setup() {


        fixture = new GDXFacebookStub(new GDXFacebookConfig());
    }

    @Test
    public void abc() {

    }
}

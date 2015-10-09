package de.tomgrill.gdxfacebook.tests.core;

import org.junit.Test;

import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookLoader;

import static org.junit.Assert.assertTrue;


public class GDXFacebookLoaderUnitTests {

    @Test(expected = NullPointerException.class)
    public void throwsWhenGDXFacebookConfigInConstructorIsNull() {
        new GDXFacebookLoader(null);
    }

    @Test(expected = RuntimeException.class)
    public void throwsWhenPREF_FILENAMEIsEmpty() {
        GDXFacebookConfig config = new GDXFacebookConfig();
        config.PREF_FILENAME = "";
        new GDXFacebookLoader(config);
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenPREF_FILENAMEIsNull() {
        GDXFacebookConfig config = new GDXFacebookConfig();
        config.PREF_FILENAME = null;
        new GDXFacebookLoader(config);
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenAPP_IDEIsNull() {
        GDXFacebookConfig config = new GDXFacebookConfig();
        config.APP_ID = null;
        new GDXFacebookLoader(config);
    }

    @Test(expected = NumberFormatException.class)
    public void throwsWhenAPP_IDIsNotANumericValue() {
        GDXFacebookConfig config = new GDXFacebookConfig();
        config.APP_ID = "ABC";
        new GDXFacebookLoader(config);
    }
}

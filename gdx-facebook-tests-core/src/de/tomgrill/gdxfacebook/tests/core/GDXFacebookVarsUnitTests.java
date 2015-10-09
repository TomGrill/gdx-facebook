package de.tomgrill.gdxfacebook.tests.core;

import org.junit.Test;

import de.tomgrill.gdxfacebook.android.AndroidGDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookVars;
import de.tomgrill.gdxfacebook.desktop.DesktopGDXFacebook;
import de.tomgrill.gdxfacebook.html.HTMLGDXFacebook;
import de.tomgrill.gdxfacebook.ios.IOSGDXFacebook;

import static org.junit.Assert.assertEquals;

public class GDXFacebookVarsUnitTests {

    @Test
    public void desktopGDXFacebookNameIsValid() {
        assertEquals(GDXFacebookVars.CLASSNAME_DESKTOP, DesktopGDXFacebook.class.getCanonicalName());
    }


    @Test
    public void htmlGDXFacebookNameIsValid() {
        assertEquals(GDXFacebookVars.CLASSNAME_HTML, HTMLGDXFacebook.class.getCanonicalName());
    }


    @Test
    public void iosGDXFacebookNameIsValid() {
        assertEquals(GDXFacebookVars.CLASSNAME_IOS, IOSGDXFacebook.class.getCanonicalName());
    }


    @Test
    public void androidGDXFacebookNameIsValid() {
        assertEquals(GDXFacebookVars.CLASSNAME_ANDROID, AndroidGDXFacebook.class.getCanonicalName());
    }
}

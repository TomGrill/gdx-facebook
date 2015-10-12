package de.tomgrill.gdxfacebook.tests.core.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import de.tomgrill.gdxfacebook.core.utils.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilsUnitTests {

    @Test
    public void permissionsArrayToString() {
        Array<String> permissions = new Array<String>();
        permissions.add("abc");
        permissions.add("def");
        permissions.add("123");

        assertEquals("abc,def,123", Utils.permissionsArrayToString(permissions));
    }


    @Test
    public void isValidSuccessfulLoginURL() {
        String testURL = "https://www.facebook.com/connect/login_success.html#access_token=CAAqyZEeY&expires_in=5182959";
        assertTrue(Utils.isValidSuccessfulLoginURL(testURL));
    }

    @Test
    public void isValidErrorLoginURL() {
        String testURL = "https://www.facebook.com/connect/login_success.html?error=access_denied&error_code=200&error_description=Permissions+error&error_reason=user_denied#_=_";
        assertTrue(Utils.isValidErrorLoginURL(testURL));
    }


    @Test
    public void parseQueryTest() {
        try {
            ArrayMap<String, String> result = Utils.parseQuery("access_token=CAAqyZEeY&expires_in=5182959");

            assertEquals("access_token", result.getKeyAt(0));
            assertEquals("CAAqyZEeY", result.getValueAt(0));

            assertEquals("expires_in", result.getKeyAt(1));
            assertEquals("5182959", result.getValueAt(1));

        } catch (UnsupportedEncodingException e) {
        }
    }


}

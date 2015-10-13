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


package de.tomgrill.gdxfacebook.tests.core.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

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
        assertTrue(Utils.isValidSuccessfulSignInURL(testURL));
    }

    @Test
    public void isValidErrorLoginURL() {
        String testURL = "https://www.facebook.com/connect/login_success.html?error=access_denied&error_code=200&error_description=Permissions+error&error_reason=user_denied#_=_";
        assertTrue(Utils.isValidErrorSignInURL(testURL));
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

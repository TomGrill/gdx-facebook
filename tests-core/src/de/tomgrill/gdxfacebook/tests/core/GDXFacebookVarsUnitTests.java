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

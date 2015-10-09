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

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidEventListener;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.tomgrill.gdxfacebook.android.AndroidGDXFacebook;
import de.tomgrill.gdxfacebook.core.FallbackGDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookLoader;
import de.tomgrill.gdxfacebook.core.GDXFacebookVars;
import de.tomgrill.gdxfacebook.desktop.DesktopGDXFacebook;
import de.tomgrill.gdxfacebook.html.HTMLGDXFacebook;
import de.tomgrill.gdxfacebook.ios.IOSGDXFacebook;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ClassReflection.class, Constructor.class, Class.class, Field.class, Method.class})
public class GDXFacebookLoaderUnitTests {

    GDXFacebook facebook;


    Constructor mockConstructor;
    GDXFacebook mockFacebook;
    Field mockField;
    Object mockObject;
    Method mockMethod;

    @Before
    public void setup() {
        mockStatic(ClassReflection.class);
    }

    @Test
    public void ifNoPlatformGDXFacebookCanBeFoundReturnFallbackGDXFacebook() {

        Application mockApplication = mock(Application.class);
        when(mockApplication.getType()).thenReturn(null);
        Gdx.app = mockApplication;

        facebook = GDXFacebookLoader.install(new GDXFacebookConfig());
        assertTrue(facebook instanceof FallbackGDXFacebook);
    }


    @Test
    public void returnAndroidGDXFacebookWhenOnAndroid_core_app_Activity() {

        androidPremocking();

        when(ClassReflection.isAssignableFrom(Activity.class, mockObject.getClass())).thenReturn(true);

        androidPostmocking();

        facebook = GDXFacebookLoader.install(new GDXFacebookConfig());
        assertTrue(facebook instanceof AndroidGDXFacebook);
    }

    @Test
    public void returnAndroidGDXFacebookWhenOnAndroid_core_support_v4_app_Fragment() {

        androidPremocking();

        when(ClassReflection.isAssignableFrom(Activity.class, mockObject.getClass())).thenReturn(false);

        try {
            when(ClassReflection.forName("core.support.v4.app.Fragment")).thenReturn(Fragment.class);
            when(ClassReflection.isAssignableFrom(Fragment.class, mockObject.getClass())).thenReturn(true);
            when(ClassReflection.getMethod(Fragment.class, "getActivity")).thenReturn(mockMethod);
            when(mockMethod.invoke(mockObject)).thenReturn(mockFacebook);

        } catch (ReflectionException e) {
            e.printStackTrace();
        }

        androidPostmocking();

        facebook = GDXFacebookLoader.install(new GDXFacebookConfig());
        assertTrue(facebook instanceof AndroidGDXFacebook);
    }

    @Test
    public void returnAndroidGDXFacebookWhenOnAndroid_core_app_Fragment() {

        androidPremocking();

        when(ClassReflection.isAssignableFrom(Activity.class, mockObject.getClass())).thenReturn(false);

        try {
            when(ClassReflection.forName("core.support.v4.app.Fragment")).thenReturn(null);
            when(ClassReflection.forName("core.app.Fragment")).thenReturn(android.app.Fragment.class);

            when(ClassReflection.isAssignableFrom(android.app.Fragment.class, mockObject.getClass())).thenReturn(true);
            when(ClassReflection.getMethod(android.app.Fragment.class, "getActivity")).thenReturn(mockMethod);
            when(mockMethod.invoke(mockObject)).thenReturn(mockFacebook);

        } catch (ReflectionException e) {
            e.printStackTrace();
        }

        androidPostmocking();

        facebook = GDXFacebookLoader.install(new GDXFacebookConfig());
        assertTrue(facebook instanceof AndroidGDXFacebook);
    }

    private void androidPremocking() {

        Application mockApplication = mock(Application.class);
        when(mockApplication.getType()).thenReturn(Application.ApplicationType.Android);
        Gdx.app = mockApplication;

        try {
            mockConstructor = PowerMockito.mock(Constructor.class);
            mockFacebook = mock(AndroidGDXFacebook.class);
            mockField = mock(Field.class);
            mockObject = mock(Object.class);
            mockMethod = mock(Method.class);

            when(ClassReflection.forName(GDXFacebookVars.CLASSNAME_ANDROID)).thenReturn(AndroidGDXFacebook.class);
            when(ClassReflection.getConstructor(AndroidGDXFacebook.class, GDXFacebookConfig.class)).thenReturn(mockConstructor);
            when(mockConstructor.newInstance(anyObject())).thenReturn(mockFacebook);


            when(ClassReflection.forName("com.badlogic.gdx.Gdx")).thenReturn(Gdx.class);
            when(ClassReflection.getField(Gdx.class, "app")).thenReturn(mockField);
            when(mockField.get(null)).thenReturn(mockObject);


            when(ClassReflection.forName("com.badlogic.gdx.backends.core.AndroidEventListener")).thenReturn(AndroidEventListener.class);
            when(ClassReflection.forName("core.app.Activity")).thenReturn(Activity.class);

        } catch (ReflectionException e) {
            e.printStackTrace();
        }
    }

    private void androidPostmocking() {
        try {

            when(ClassReflection.getConstructor(AndroidGDXFacebook.class, Activity.class, GDXFacebookConfig.class)).thenReturn(mockConstructor);
            when(mockConstructor.newInstance(anyObject(), any(GDXFacebookConfig.class))).thenReturn(mockFacebook);
            when(ClassReflection.getMethod(mockObject.getClass(), "addAndroidEventListener", AndroidEventListener.class)).thenReturn(mockMethod);

        } catch (ReflectionException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void returnIOSGDXFacebookWhenOnIOS() {
        Application mockApplication = mock(Application.class);
        when(mockApplication.getType()).thenReturn(Application.ApplicationType.iOS);
        Gdx.app = mockApplication;

        try {
            Constructor mockConstructor = PowerMockito.mock(Constructor.class);
            GDXFacebook mockFacebook = mock(IOSGDXFacebook.class);

            when(ClassReflection.forName(GDXFacebookVars.CLASSNAME_IOS)).thenReturn(IOSGDXFacebook.class);
            when(ClassReflection.getConstructor(IOSGDXFacebook.class, GDXFacebookConfig.class)).thenReturn(mockConstructor);
            when(mockConstructor.newInstance(anyObject())).thenReturn(mockFacebook);

        } catch (ReflectionException e) {
            e.printStackTrace();
        }

        facebook = GDXFacebookLoader.install(new GDXFacebookConfig());
        assertTrue(facebook instanceof IOSGDXFacebook);
    }

    @Test
    public void returnDesktopGDXFacebookWhenOnDesktop() {
        Application mockApplication = mock(Application.class);
        when(mockApplication.getType()).thenReturn(Application.ApplicationType.Desktop);
        Gdx.app = mockApplication;

        try {
            Constructor mockConstructor = PowerMockito.mock(Constructor.class);
            GDXFacebook mockFacebook = mock(DesktopGDXFacebook.class);

            when(ClassReflection.forName(GDXFacebookVars.CLASSNAME_DESKTOP)).thenReturn(DesktopGDXFacebook.class);
            when(ClassReflection.getConstructor(DesktopGDXFacebook.class, GDXFacebookConfig.class)).thenReturn(mockConstructor);
            when(mockConstructor.newInstance(anyObject())).thenReturn(mockFacebook);

        } catch (ReflectionException e) {
            e.printStackTrace();
        }

        facebook = GDXFacebookLoader.install(new GDXFacebookConfig());
        assertTrue(facebook instanceof DesktopGDXFacebook);
    }

    @Test
    public void returnHTMLGDXFacebookWhenOnWebGL() {
        Application mockApplication = mock(Application.class);
        when(mockApplication.getType()).thenReturn(Application.ApplicationType.WebGL);
        Gdx.app = mockApplication;

        try {
            Constructor mockConstructor = PowerMockito.mock(Constructor.class);
            GDXFacebook mockFacebook = mock(HTMLGDXFacebook.class);

            when(ClassReflection.forName(GDXFacebookVars.CLASSNAME_HTML)).thenReturn(HTMLGDXFacebook.class);
            when(ClassReflection.getConstructor(HTMLGDXFacebook.class, GDXFacebookConfig.class)).thenReturn(mockConstructor);
            when(mockConstructor.newInstance(anyObject())).thenReturn(mockFacebook);

        } catch (ReflectionException e) {
            e.printStackTrace();
        }

        facebook = GDXFacebookLoader.install(new GDXFacebookConfig());
        assertTrue(facebook instanceof HTMLGDXFacebook);
    }


    @Test(expected = NullPointerException.class)
    public void throwsWhenGDXFacebookConfigInConstructorIsNull() {
        GDXFacebookLoader.install(null);
    }

    @Test(expected = RuntimeException.class)
    public void throwsWhenPREF_FILENAMEIsEmpty() {
        GDXFacebookConfig config = new GDXFacebookConfig();
        config.PREF_FILENAME = "";
        GDXFacebookLoader.install(config);
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenPREF_FILENAMEIsNull() {
        GDXFacebookConfig config = new GDXFacebookConfig();
        config.PREF_FILENAME = null;
        GDXFacebookLoader.install(config);
    }

    @Test(expected = NullPointerException.class)
    public void throwsWhenAPP_IDEIsNull() {
        GDXFacebookConfig config = new GDXFacebookConfig();
        config.APP_ID = null;
        GDXFacebookLoader.install(config);
    }

    @Test(expected = NumberFormatException.class)
    public void throwsWhenAPP_IDIsNotANumericValue() {
        GDXFacebookConfig config = new GDXFacebookConfig();
        config.APP_ID = "ABC";
        GDXFacebookLoader.install(config);
    }
}

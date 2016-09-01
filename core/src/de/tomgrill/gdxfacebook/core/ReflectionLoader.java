/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tomgrill.gdxfacebook.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class ReflectionLoader {
    public static GDXFacebook load(GDXFacebookConfig config) {

        try {
            Object loaderObj;
            Class<?> loaderCls = null;

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                loaderCls = ClassReflection.forName("de.tomgrill.gdxfacebook.android.AndroidFacebookLoader");
            }

            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                loaderCls = ClassReflection.forName("de.tomgrill.gdxfacebook.desktop.DesktopFacebookLoader");
            }

            if (Gdx.app.getType() == Application.ApplicationType.iOS) {
                // tell robovm and moe apart
                try {
                    loaderCls = ClassReflection.forName("de.tomgrill.gdxfacebook.ios.IOSFacebookLoader");
                } catch (ReflectionException e) {
                    loaderCls = ClassReflection.forName("de.tomgrill.gdxfacebook.iosmoe.IOSMOEFacebookLoader");
                }
            }

            if (loaderCls != null) {
                loaderObj = ClassReflection.getConstructor(loaderCls).newInstance();

                Gdx.app.debug(GDXFacebookVars.LOG_TAG, GDXFacebookVars.LOG_TAG + " for " + Gdx.app.getType() + " installed successfully.");

                return ((FacebookLoader) loaderObj).load(config);
            }

        } catch (ReflectionException e) {
            Gdx.app.error(GDXFacebookVars.LOG_TAG, "Error installing " + GDXFacebookVars.LOG_TAG + " for " + Gdx.app.getType() + "\n");
            Gdx.app.error(GDXFacebookVars.LOG_TAG, "Did you add >> compile \"de.tomgrill.gdxfacebook:gdx-facebook-" + artifactByAppType(Gdx.app.getType()) + ":" + GDXFacebookVars.VERSION
                    + "\" << to your gradle dependencies? View https://github.com/TomGrill/gdx-facebook/wiki for more information.\n");

            if (Gdx.app.getType() == Application.ApplicationType.iOS) {
                Gdx.app.error(GDXFacebookVars.LOG_TAG, "or in cas you use multi-os-engine >> compile \"de.tomgrill.gdxfacebook:gdx-facebook-ios-moe:" + GDXFacebookVars.VERSION
                        + "\" <<\n");

            }
        }

        return new FallbackGDXFacebook();
    }

    private static String artifactByAppType(final Application.ApplicationType type) {
        if (type == Application.ApplicationType.Android) {
            return "android";
        }
        if (type == Application.ApplicationType.iOS) {
            return "ios";
        }
        if (type == Application.ApplicationType.Desktop) {
            return "desktop";
        }
        return "unknown_type";
    }
}

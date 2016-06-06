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
                loaderCls = ClassReflection.forName("de.tomgrill.gdxfacebook.ios.IOSFacebookLoader");
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

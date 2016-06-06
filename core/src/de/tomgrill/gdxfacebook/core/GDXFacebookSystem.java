package de.tomgrill.gdxfacebook.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import de.tomgrill.gdxfacebook.html.HTMLFacebookLoader;

public class GDXFacebookSystem {

    private static GDXFacebook gdxFacebook;

    public static GDXFacebook install(GDXFacebookConfig config) {

        if(Gdx.app.getType() == Application.ApplicationType.WebGL) {
            gdxFacebook = (new HTMLFacebookLoader()).load(config);
        } else {
            gdxFacebook = ReflectionLoader.load(config);
        }
        return gdxFacebook;
    }

    private static void validateConfig(GDXFacebookConfig config) {
        if (config == null) {
            throw new NullPointerException(GDXFacebookConfig.class.getSimpleName() + "may not be null.");
        }

        if (config.PREF_FILENAME == null) {
            throw new NullPointerException("GDXFacebookConfig.class.getSimpleName() + \": PREF_FILENAME may bot be null.");
        }

        if (config.PREF_FILENAME.length() == 0) {
            throw new RuntimeException(GDXFacebookConfig.class.getSimpleName() + ": PREF_FILENAME is empty.");
        }

        if (config.APP_ID == null) {
            throw new NullPointerException("GDXFacebookConfig.class.getSimpleName() + \": APP_ID may bot be null.");
        }

        Long.valueOf(config.APP_ID); // kinda stupid but simple way to check if APP_ID is a valid Long
    }

    public static GDXFacebook getFacebook() {
        return gdxFacebook;
    }
}

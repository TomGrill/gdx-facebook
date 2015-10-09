package de.tomgrill.gdxfacebook.core;

public class GDXFacebookLoader {

    public GDXFacebookLoader(GDXFacebookConfig config) {
        validateConfig(config);
    }

    private void validateConfig(GDXFacebookConfig config) {
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

        Long.valueOf(config.APP_ID);

    }
}

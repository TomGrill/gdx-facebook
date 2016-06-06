package de.tomgrill.gdxfacebook.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.FacebookLoader;

public class AndroidFacebookLoader implements FacebookLoader {

    @Override
    public GDXFacebook load(GDXFacebookConfig config) {

        AndroidApplication androidApplication = (AndroidApplication) Gdx.app;
        AndroidGDXFacebook androidGDXFacebook = new AndroidGDXFacebook(androidApplication, config);
        androidApplication.addAndroidEventListener(androidGDXFacebook);

        return androidGDXFacebook;
    }
}

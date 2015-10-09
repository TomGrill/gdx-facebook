package de.tomgrill.gdxfacebook.android;

import android.app.Activity;
import android.content.Intent;

import com.badlogic.gdx.backends.android.AndroidEventListener;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;

public class AndroidGDXFacebook extends GDXFacebook implements AndroidEventListener {
    public AndroidGDXFacebook(final Activity activity, final GDXFacebookConfig config) {
        super(config);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}

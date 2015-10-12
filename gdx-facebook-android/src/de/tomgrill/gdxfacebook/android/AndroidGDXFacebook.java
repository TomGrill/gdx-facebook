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



package de.tomgrill.gdxfacebook.android;

import android.app.Activity;
import android.content.Intent;

import com.badlogic.gdx.backends.android.AndroidEventListener;
import com.badlogic.gdx.utils.Array;

import de.tomgrill.gdxfacebook.core.AccessToken;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.SignInResult;
import de.tomgrill.gdxfacebook.core.SignInMode;

public class AndroidGDXFacebook extends GDXFacebook implements AndroidEventListener {
    public AndroidGDXFacebook(final Activity activity, final GDXFacebookConfig config) {
        super(config);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback) {

    }

    @Override
    public AccessToken getAccessToken() {
        return null;
    }

    @Override
    public void signOut() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }
}

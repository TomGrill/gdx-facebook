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

package de.tomgrill.gdxfacebook.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.TimeUtils;
import de.tomgrill.gdxfacebook.core.*;
import de.tomgrill.gdxfacebook.core.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class DesktopGDXFacebook extends GDXFacebookBasic implements JXBrowserCallbackHandler {


    public DesktopGDXFacebook(GDXFacebookConfig config) {
        super(config);
    }


    @Override
    public void signIn(SignInMode mode, final Array<String> permissions, final GDXFacebookCallback<SignInResult> callback) {
        this.callback = callback;
        this.permissions = permissions;

        loadAccessToken();

        if (accessToken != null) {
            startSilentSignIn();
        } else {
            startGUISignIn();
        }

    }

    @Override
    public void showGameRequest(GDXFacebookGameRequest request, GDXFacebookCallback<GameRequestResult> callback) {
        gameRequest(request, callback);
    }

    @Override
    public void gameRequest(GDXFacebookGameRequest request, GDXFacebookCallback<GameRequestResult> callback) {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Game Request not supported on Desktop");
    }

    @Override
    protected void startGUISignIn() {
        if (accessToken == null) {
            JXBrowser.login(permissions, config, this);
            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting GUI sign in.");
        }
    }


    @Override
    public boolean isSignedIn() {
        return accessToken != null;
    }

    @Override
    public boolean isLoaded() {
        return true;
    }


    @Override
    public void handleURL(String url) {
        if (Utils.isValidErrorSignInURL(url)) {
            handleErrorSignIn(url);
        } else if (Utils.isValidSuccessfulSignInURL(url)) {
            handleSuccessSignIn(url);
        }
    }

    private void handleSuccessSignIn(String url) {
        try {
            URL urlObj = new URL(url);
            ArrayMap<String, String> params = Utils.parseQuery(urlObj.getRef());

            /** declare a 60 days expiration as default */
            long expiresInSecondsFromNow = 60 * 60 * 24;

            try {
                expiresInSecondsFromNow = Long.parseLong(params.get("expires_in"));
            } catch (NumberFormatException nfe) {
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Given expires_in value is not a valid Long. 60 days expiration assumed and used.");
            }

            long expiresInMillisFromNow = expiresInSecondsFromNow * 1000L;
            long expiresInMillisTimestamp = expiresInMillisFromNow + TimeUtils.millis();

            accessToken = new GDXFacebookAccessToken(params.get("access_token"), expiresInMillisTimestamp);
            storeNewToken(accessToken);

            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign successful. User token: " + accessToken.getToken());

            callback.onSuccess(new SignInResult(accessToken, "Sign in successful."));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void handleErrorSignIn(String url) {
        try {
            URL urlObj = new URL(url);

            String errorMessage = urlObj.getQuery();
            if (errorMessage != null) {
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Error while trying to sign in: " + errorMessage);
                callback.onError(new GDXFacebookError(errorMessage));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handleCancel() {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign in has been fail");
        callback.onCancel();
    }

    @Override
    public void signOut(boolean keepSessionData) {
        super.signOut(keepSessionData);

        if(keepSessionData == SignOutMode.DELETE_SESSION_DATA) {
            deleteTokenData();
        }
    }
}

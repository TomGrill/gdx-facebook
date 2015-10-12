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

package de.tomgrill.gdxfacebook.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import de.tomgrill.gdxfacebook.core.AccessToken;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookGraphRequest;
import de.tomgrill.gdxfacebook.core.GDXFacebookVars;
import de.tomgrill.gdxfacebook.core.GraphError;
import de.tomgrill.gdxfacebook.core.JsonResult;
import de.tomgrill.gdxfacebook.core.SignInResult;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.core.utils.Utils;

public class DesktopGDXFacebook extends GDXFacebook implements JXBrowserCallbackHandler, GDXFacebookCallback<JsonResult> {

    private GDXFacebookCallback callback;

    private Preferences preferences;

    private AccessToken accessToken;

    private Array<String> permissions;

    public DesktopGDXFacebook(GDXFacebookConfig config) {
        super(config);
        preferences = Gdx.app.getPreferences(config.PREF_FILENAME);

        loadAccessToken();
    }

    private void loadAccessToken() {
        String token = preferences.getString("access_token", null);
        long expiresAt = preferences.getLong("expires_at", 0);


        if (token != null && expiresAt > 0) {
            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Loaded existing accessToken: " + token);
            accessToken = new AccessToken(token, expiresAt);
        } else {
            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Could not load existing accessToken");
        }
    }

    @Override
    public void signIn(SignInMode mode, final Array<String> permissions, final GDXFacebookCallback<SignInResult> callback) {
        this.callback = callback;
        this.permissions = permissions;

        if (accessToken != null) {
            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Skipping sign in. User is already logged in.");
            callback.onSuccess(new SignInResult(accessToken, "Login successful (already logged in)."));
            return;
        } else {
            loadAccessToken();
        }

        if (accessToken != null) {
            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting silent login.");
            GDXFacebookGraphRequest request = new GDXFacebookGraphRequest();
            request.setNode("me");
            request.useCurrentAccessToken();
            newGraphRequest(request, this);
        } else {
            startGUISignIn(this.permissions);
        }


    }

    private void startGUISignIn(Array<String> permissions) {
        if (accessToken == null) {
            JXBrowser.login(permissions, config, this);
            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting GUI SignIn.");
        }
    }

    @Override
    public AccessToken getAccessToken() {
        return accessToken;
    }

    @Override
    public void signOut() {
        accessToken = null;
    }

    @Override
    public boolean isSignedIn() {
        return accessToken != null;
    }


    @Override
    public void handleURL(String url) {
        if (Utils.isValidErrorLoginURL(url)) {
            handleErrorSignIn(url);
        } else if (Utils.isValidSuccessfulLoginURL(url)) {
            handleSuccessSignIn(url);
        }
    }

    private void handleSuccessSignIn(String url) {
        try {
            URL urlObj = new URL(url);
            ArrayMap<String, String> params = Utils.parseQuery(urlObj.getRef());
            accessToken = new AccessToken(params.get("access_token"), Integer.parseInt(params.get("expires_in")) + TimeUtils.millis() / 1000L);
            storeNewToken(accessToken);

            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign successful. User token: " + accessToken.getToken());

            callback.onSuccess(new SignInResult(accessToken, "Login successful."));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void storeNewToken(AccessToken token) {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Storing new accessToken: " + token.getToken());
        preferences.putString("access_token", token.getToken());
        preferences.putLong("expires_at", token.getExpiresAt());
        preferences.flush();
    }

    private void handleErrorSignIn(String url) {
        try {
            URL urlObj = new URL(url);

            String errorMessage = urlObj.getQuery();
            if (errorMessage != null) {
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Error while trying to sign in: " + errorMessage);
                callback.onError(new GraphError(errorMessage));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handleCancel() {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign in has been cancelled");
        callback.onCancel();
    }

    @Override
    public void onSuccess(JsonResult result) {
        JsonValue value = result.getJsonValue();

        if (value.has("name") && value.has("id")) {
            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent login successful. Current token is still valid.");
            callback.onSuccess(new SignInResult(accessToken, "Silent login successful. Current token is still valid."));
        } else {
            signOut();
            Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent login error: " + value.toString());
            callback.onError(new GraphError(value.toString()));
        }
        startGUISignIn(permissions);
    }

    @Override
    public void onError(GraphError error) {
        signOut();
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent login error: " + error.getErrorMesssage());
        callback.onError(error);
        startGUISignIn(permissions);
    }

    @Override
    public void onFail(Throwable t) {
        signOut();
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent login failed: " + t.getMessage());
        callback.onFail(t);
        startGUISignIn(permissions);
    }

    @Override
    public void onCancel() {
        signOut();
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Silent login cancelled");
        callback.onCancel();
        startGUISignIn(permissions);
    }
}

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

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import de.tomgrill.gdxfacebook.core.AccessToken;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GraphError;
import de.tomgrill.gdxfacebook.core.JsonResult;
import de.tomgrill.gdxfacebook.core.LoginResult;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.core.utils.Utils;

public class DesktopGDXFacebook extends GDXFacebook implements JXBrowserCallbackHandler {

    private GDXFacebookCallback callback;

    public DesktopGDXFacebook(GDXFacebookConfig config) {
        super(config);
    }

    @Override
    public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback callback) {
        this.callback = callback;
        JXBrowser.login(permissions, config, this);
    }


    @Override
    public void handleURL(String url) {
        if (Utils.isValidErrorLoginURL(url)) {
            handleErrorLogin(url);
        } else if (Utils.isValidSuccessfulLoginURL(url)) {
            handleSuccessLogin(url);
        }
    }

    private void handleSuccessLogin(String url) {
        try {
            URL urlObj = new URL(url);
            ArrayMap<String, String> params = Utils.parseQuery(urlObj.getRef());
            AccessToken token = new AccessToken(params.get("access_token"), Integer.parseInt(params.get("expires_in")));
            callback.onSuccess(new LoginResult(token, "Login successful."));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void handleErrorLogin(String url) {
        try {
            URL urlObj = new URL(url);

            String errorMessage = urlObj.getQuery();
            if (errorMessage != null) {
                callback.onError(new GraphError(errorMessage));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel() {
        callback.onCancel();
    }
}

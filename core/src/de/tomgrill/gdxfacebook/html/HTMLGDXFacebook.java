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

package de.tomgrill.gdxfacebook.html;

import com.badlogic.gdx.utils.Array;
import de.tomgrill.gdxfacebook.core.*;

public class HTMLGDXFacebook implements GDXFacebook {

    private GDXFacebookConfig config;

    public HTMLGDXFacebook(GDXFacebookConfig config) {
        this.config = config;
    }

    @Override
    public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback) {

        JSNIFacebookSDK.FBLogin();
        JSNIJavascript.consoleLog("login");


    }

    @Override
    public void showGameRequest(GDXFacebookGameRequest request, GDXFacebookCallback<GameRequestResult> callback) {

    }

    @Override
    public void gameRequest(GDXFacebookGameRequest request, GDXFacebookCallback<GameRequestResult> callback) {

    }

    @Override
    public void newGraphRequest(Request request, GDXFacebookCallback<JsonResult> callback) {

    }

    @Override
    public void api(Request request, GDXFacebookCallback<JsonResult> callback) {

    }

    @Override
    public void signOut(boolean keepSessionData) {

    }

    @Override
    public void signOut() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public GDXFacebookAccessToken getAccessToken() {
        return null;
    }


}
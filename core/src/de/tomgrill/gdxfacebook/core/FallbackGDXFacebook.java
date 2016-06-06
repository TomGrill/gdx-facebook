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


package de.tomgrill.gdxfacebook.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public class FallbackGDXFacebook implements GDXFacebook {

	@Override
	public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback) {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign in error. " + GDXFacebookVars.LOG_TAG + " is not installed.");
		callback.onError(new GDXFacebookError("Sign in error. " + GDXFacebookVars.LOG_TAG + " is not installed."));
	}

	@Override
	public void showGameRequest(GDXFacebookGameRequest request, GDXFacebookCallback<GameRequestResult> callback) {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Cannot show GameRequest. " + GDXFacebookVars.LOG_TAG + " is not installed.");
		callback.onError(new GDXFacebookError("Cannot show GameRequest." + GDXFacebookVars.LOG_TAG + " is not installed."));
	}

	@Override
	public void gameRequest(GDXFacebookGameRequest request, GDXFacebookCallback<GameRequestResult> callback) {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Cannot show GameRequest. " + GDXFacebookVars.LOG_TAG + " is not installed.");
		callback.onError(new GDXFacebookError("Cannot do graph API request." + GDXFacebookVars.LOG_TAG + " is not installed."));
	}

	@Override
	public void newGraphRequest(Request request, GDXFacebookCallback<JsonResult> callback) {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Cannot do graph API request. " + GDXFacebookVars.LOG_TAG + " is not installed.");
		callback.onError(new GDXFacebookError("Cannot show GameRequest." + GDXFacebookVars.LOG_TAG + " is not installed."));
	}

	@Override
	public void graph(Request request, GDXFacebookCallback<JsonResult> callback) {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Cannot do graph API request. " + GDXFacebookVars.LOG_TAG + " is not installed.");
		callback.onError(new GDXFacebookError("Cannot do graph API request." + GDXFacebookVars.LOG_TAG + " is not installed."));
	}

	@Override
	public void signOut(boolean keepSessionData) {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Cannot sign out. " + GDXFacebookVars.LOG_TAG + " is not installed.");
	}

	@Override
	@Deprecated
	public GDXFacebookAccessToken getAccessToken() {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Cannot return accessToken. " + GDXFacebookVars.LOG_TAG + " is not installed.");
		return null;
	}

	@Override
	public boolean isLoaded() {
		return true;
	}

	@Override
	public void signOut() {
		signOut(true);
	}

	@Override
	public boolean isSignedIn() {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "User is not signed in. " + GDXFacebookVars.LOG_TAG + " is not installed.");
		return false;
	}
}

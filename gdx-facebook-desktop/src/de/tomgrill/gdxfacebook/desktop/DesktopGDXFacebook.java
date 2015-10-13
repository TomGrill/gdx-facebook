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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookAccessToken;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookVars;
import de.tomgrill.gdxfacebook.core.GraphError;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.core.SignInResult;
import de.tomgrill.gdxfacebook.core.utils.Utils;

public class DesktopGDXFacebook extends GDXFacebook implements JXBrowserCallbackHandler {


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
			accessToken = new GDXFacebookAccessToken(params.get("access_token"), Integer.parseInt(params.get("expires_in")) + TimeUtils.millis() / 1000L);
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

}

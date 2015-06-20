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

import java.util.Collection;

import android.app.Activity;
import android.content.Intent;

import com.badlogic.gdx.backends.android.AndroidEventListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.core.GDXFacebookLoginResult;

public class AndroidGDXFacebook extends GDXFacebook implements AndroidEventListener {

	private final Activity activity;
	private final GDXFacebookConfig config;

	private CallbackManager callbackManager;

	private LoginManager loginManager;

	public AndroidGDXFacebook(final Activity activity, final GDXFacebookConfig config) {
		this.activity = activity;
		this.config = config;

		FacebookSdk.sdkInitialize(activity.getApplicationContext());
		callbackManager = CallbackManager.Factory.create();

		loginManager = LoginManager.getInstance();
		loginManager.setLoginBehavior(LoginBehavior.SSO_WITH_FALLBACK);
	}

	@Override
	public boolean isLoggedIn() {
		return AccessToken.getCurrentAccessToken() != null;
	}

	@Override
	public void logOut() {
		loginManager.logOut();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void loginWithReadPermissions(Collection<String> permissions, GDXFacebookCallback<GDXFacebookLoginResult> callback) {
		login(permissions, callback, false);
	}

	@Override
	public void loginWithPublishPermissions(Collection<String> permissions, GDXFacebookCallback<GDXFacebookLoginResult> callback) {
		login(permissions, callback, true);
	}

	private void login(Collection<String> permissions, final GDXFacebookCallback<GDXFacebookLoginResult> callback, boolean withPublishPermissions) {

		if (AccessToken.getCurrentAccessToken() != null) {

			GDXFacebookLoginResult result = new GDXFacebookLoginResult();
			result.setAccessToken(AccessToken.getCurrentAccessToken().getToken());
			callback.onSuccess(result);

		} else {

			loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

				@Override
				public void onSuccess(LoginResult loginResult) {

					GDXFacebookLoginResult result = new GDXFacebookLoginResult();
					result.setAccessToken(AccessToken.getCurrentAccessToken().getToken());
					callback.onSuccess(result);
				}

				@Override
				public void onCancel() {
					callback.onCancel();
				}

				@Override
				public void onError(FacebookException e) {
					GDXFacebookError error = new GDXFacebookError();
					error.setErrorMessage(e.getMessage());
					callback.onError(error);
				}
			});

			if (withPublishPermissions) {
				loginManager.logInWithPublishPermissions(activity, permissions);
			} else {
				loginManager.logInWithReadPermissions(activity, permissions);
			}

		}
	}

	@Override
	public String getAccessToken() {
		return AccessToken.getCurrentAccessToken().getToken();
	}

}

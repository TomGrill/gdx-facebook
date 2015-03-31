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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import de.tomgrill.gdxfacebook.core.FacebookAPI;
import de.tomgrill.gdxfacebook.core.FacebookConfig;
import de.tomgrill.gdxfacebook.core.FacebookUtils;
import de.tomgrill.gdxfacebook.core.ResponseError;
import de.tomgrill.gdxfacebook.core.ResponseListener;

public class AndroidFacebookAPI extends FacebookAPI implements AndroidEventListener {
	private final Activity activity;
	private final FacebookConfig config;

	private CallbackManager callbackManager;

	public AndroidFacebookAPI(final Activity activity, final FacebookConfig config) {
		this.activity = activity;
		this.config = config;

		FacebookSdk.sdkInitialize(activity.getApplicationContext());
		callbackManager = CallbackManager.Factory.create();

	}

	@Override
	public boolean isSignedin() {
		return isSignedin;
	}

	@Override
	public void signout() {
		isSignedin = false;
		LoginManager.getInstance().logOut();

	}

	@Override
	public void signin(final boolean allowGUI, final ResponseListener responseListener) {
		isSignedin = false;

		if (AccessToken.getCurrentAccessToken() != null) {
			isSignedin = true;
			setAccessToken(AccessToken.getCurrentAccessToken().getToken());
			responseListener.success();
		} else {

			LoginBehavior loginBehavior = LoginBehavior.SSO_WITH_FALLBACK;
			//
			// if (allowGUI) {
			// loginBehavior = LoginBehavior.SSO_WITH_FALLBACK;
			// }

			LoginManager.getInstance().setLoginBehavior(loginBehavior);
			// LoginManager.getInstance().setDefaultAudience(dfdf);

			LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
				@Override
				public void onSuccess(LoginResult loginResult) {
					isSignedin = true;
					setAccessToken(loginResult.getAccessToken().getToken());
					responseListener.success();
				}

				@Override
				public void onCancel() {
					responseListener.cancel();
				}

				@Override
				public void onError(FacebookException arg0) {
					// TODO evaluate arg0
					ResponseError error = new ResponseError();
					error.setCode(-1);
					error.setMessage("Could not (re)open Facebook session.");
					responseListener.error(error);

				}

			});

			LoginManager.getInstance().logInWithReadPermissions(activity, FacebookUtils.permissionSplitToList(config.PERMISSIONS));
		}
	}

	@Override
	public boolean isLoaded() {
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
}

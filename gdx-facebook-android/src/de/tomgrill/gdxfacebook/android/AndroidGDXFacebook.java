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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidEventListener;
import com.badlogic.gdx.utils.Array;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.Collection;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookAccessToken;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookVars;
import de.tomgrill.gdxfacebook.core.GraphError;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.core.SignInResult;

public class AndroidGDXFacebook extends GDXFacebook implements AndroidEventListener {

	private Activity activity;
	private CallbackManager callbackManager;
	private String userId;
	private SignInMode signInMode;


	public AndroidGDXFacebook(final Activity activity, final GDXFacebookConfig config) {
		super(config);
		this.activity = activity;

		FacebookSdk.sdkInitialize(activity.getApplicationContext());
		callbackManager = CallbackManager.Factory.create();

		LoginManager.getInstance().setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void startGUISignIn() {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting GUI sign in.");
		LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

			@Override
			public void onSuccess(LoginResult loginResult) {
				accessToken = new GDXFacebookAccessToken(AccessToken.getCurrentAccessToken().getToken(), AccessToken.getCurrentAccessToken().getExpires().getTime());
				storeNewToken(accessToken);
				Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign successful. User token: " + accessToken.getToken());
				callback.onSuccess(new SignInResult(accessToken, "Sign in successful."));
			}

			@Override
			public void onCancel() {
				Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign cancelled by user.");
				callback.onCancel();
			}

			@Override
			public void onError(FacebookException e) {
				Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Error while trying to sign in: " + e.getMessage());
				callback.onError(new GraphError(e.getMessage()));
			}
		});

		if (this.signInMode == SignInMode.PUBLISH) {
			LoginManager.getInstance().logInWithPublishPermissions(activity, gdxArrayToCollection(permissions));
		} else {
			LoginManager.getInstance().logInWithReadPermissions(activity, gdxArrayToCollection(permissions));
		}
	}

	private Collection<String> gdxArrayToCollection(Array<String> array) {
		Collection<String> col = new ArrayList<String>();
		for (int i = 0; i < array.size; i++) {
			col.add(array.get(i));
		}
		return col;
	}

	@Override
	public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback) {
		this.callback = callback;
		this.permissions = permissions;
		this.signInMode = mode;

		/**
		 * Check whether we can reuse an existing token:
		 *
		 * If the user has Facebook App installed
		 * AccessToken.getCurrentAccessToken() will not be NULL
		 *
		 * Only when the Facebook App is not installed we need to load the token
		 * manually.
		 * */
		loadAccessToken();

		if (accessToken == null && AccessToken.getCurrentAccessToken() != null) {
			accessToken = new GDXFacebookAccessToken(AccessToken.getCurrentAccessToken().getToken(), AccessToken.getCurrentAccessToken().getExpires().getTime());
		}


		if (accessToken != null) {
			startSilentSignIn();
		} else {
			startGUISignIn();
		}

	}


	@Override
	protected void loadAccessToken() {
		super.loadAccessToken();
		loadUserId();
	}

	@Override
	protected void storeNewToken(GDXFacebookAccessToken token) {
		super.storeNewToken(token);
		storeUserId();
	}

	private void storeUserId() {
		preferences.putString("user_id", userId);
		preferences.flush();
	}

	private void loadUserId() {
		userId = preferences.getString("user_id", null);
	}

	@Override
	public GDXFacebookAccessToken getAccessToken() {
		return accessToken;
	}

	@Override
	public void signOut() {
		accessToken = null;
		userId = null;
		FacebookSdk.sdkInitialize(activity.getApplicationContext());
		LoginManager.getInstance().logOut();
	}

	@Override
	public boolean isSignedIn() {
		return accessToken != null;
	}
}
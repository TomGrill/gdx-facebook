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

package de.tomgrill.gdxfacebook.untested.android;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;

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

import de.tomgrill.gdxfacebook.untested.core.GDXFacebook;
import de.tomgrill.gdxfacebook.untested.core.GDXFacebookAccessToken;
import de.tomgrill.gdxfacebook.untested.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.untested.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.untested.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.untested.core.GDXFacebookLoginResult;

public class AndroidGDXFacebook extends GDXFacebook implements AndroidEventListener {

	private final Activity activity;

	private CallbackManager callbackManager;

	// private LoginManager loginManager;

	private GDXFacebookAccessToken accessToken;

	public AndroidGDXFacebook(final Activity activity, final GDXFacebookConfig config) {
		super(config);
		this.activity = activity;

		FacebookSdk.sdkInitialize(activity.getApplicationContext());
		callbackManager = CallbackManager.Factory.create();

		LoginManager.getInstance().setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
	}

	@Override
	public boolean isLoggedIn() {
		return accessToken != null;
	}

	@Override
	public void logOut() {
		accessToken = null;
		storeToken(accessToken);
		FacebookSdk.sdkInitialize(activity.getApplicationContext());
		LoginManager.getInstance().logOut();
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

		/**
		 * Check whether we can reuse an existing token:
		 * 
		 * If the user has Facebook App installed
		 * AccessToken.getCurrentAccessToken() will not be NULL
		 * 
		 * Only when the Facebook App is not installed we need to load the token
		 * manually.
		 * */

		// System.out.println(loginManager.);

		if (AccessToken.getCurrentAccessToken() == null) {

			accessToken = loadAccessToken();

			if (accessToken != null) {
				AccessToken reuseToken = fromGDXFacebookToken(accessToken);
				AccessToken.setCurrentAccessToken(reuseToken);
			}
		}

		if (AccessToken.getCurrentAccessToken() != null) {

			accessToken = toGDXFacebookToken(AccessToken.getCurrentAccessToken());

			if (arePermissionsGranted(permissions)) {

				GDXFacebookLoginResult result = new GDXFacebookLoginResult();
				accessToken = toGDXFacebookToken(AccessToken.getCurrentAccessToken());
				result.setAccessToken(accessToken);
				storeToken(accessToken);
				callback.onSuccess(result);
			} else {
				/**
				 * quickfix issue #5 - TODO replace with proper code
				 * 
				 * */
				LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

					@Override
					public void onSuccess(LoginResult loginResult) {

						GDXFacebookLoginResult result = new GDXFacebookLoginResult();
						accessToken = toGDXFacebookToken(AccessToken.getCurrentAccessToken());
						storeToken(accessToken);

						result.setAccessToken(accessToken);
						callback.onSuccess(result);
					}

					@Override
					public void onCancel() {
						accessToken = null;
						callback.onCancel();
					}

					@Override
					public void onError(FacebookException e) {
						accessToken = null;
						GDXFacebookError error = new GDXFacebookError();
						error.setErrorMessage(e.getMessage());
						callback.onError(error);
					}
				});

				if (withPublishPermissions) {
					LoginManager.getInstance().logInWithPublishPermissions(activity, permissions);
				} else {
					LoginManager.getInstance().logInWithReadPermissions(activity, permissions);
				}
			}
		} else {

			LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

				@Override
				public void onSuccess(LoginResult loginResult) {

					GDXFacebookLoginResult result = new GDXFacebookLoginResult();
					accessToken = toGDXFacebookToken(AccessToken.getCurrentAccessToken());
					storeToken(accessToken);

					result.setAccessToken(accessToken);
					callback.onSuccess(result);
				}

				@Override
				public void onCancel() {
					accessToken = null;
					callback.onCancel();
				}

				@Override
				public void onError(FacebookException e) {
					accessToken = null;
					GDXFacebookError error = new GDXFacebookError();
					error.setErrorMessage(e.getMessage());
					callback.onError(error);
				}
			});

			if (withPublishPermissions) {
				LoginManager.getInstance().logInWithPublishPermissions(activity, permissions);
			} else {
				LoginManager.getInstance().logInWithReadPermissions(activity, permissions);
			}

		}
	}

	@Override
	public GDXFacebookAccessToken getAccessToken() {
		return accessToken;
	}

	private GDXFacebookAccessToken toGDXFacebookToken(AccessToken accessToken) {
		return new GDXFacebookAccessToken(accessToken.getToken(), accessToken.getApplicationId(), accessToken.getUserId(), collectionToGdxArray(accessToken.getPermissions()),
				collectionToGdxArray(accessToken.getDeclinedPermissions()), accessToken.getExpires().getTime(), accessToken.getLastRefresh().getTime());
	}

	private AccessToken fromGDXFacebookToken(GDXFacebookAccessToken accessToken) {
		return new AccessToken(accessToken.getToken(), accessToken.getApplicationId(), accessToken.getUserId(), gdxArrayToCollection(accessToken.getPermissions()),
				gdxArrayToCollection(accessToken.getDeclinedPermissions()), null, new Date(accessToken.getExpirationTime()), new Date(accessToken.getLastRefreshTime()));
	}

	private Collection<String> gdxArrayToCollection(Array<String> array) {
		Collection<String> col = new ArrayList<String>();
		for (int i = 0; i < array.size; i++) {
			col.add(array.get(i));
		}
		return col;
	}

	private Array<String> collectionToGdxArray(Collection<String> col) {
		String[] arr = new String[col.size()];
		col.toArray(arr);
		return new Array<String>(arr);
	}

}

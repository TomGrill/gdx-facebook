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


package de.tomgrill.gdxfacebook.ios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.facebook.core.FBSDKAccessToken;
import org.robovm.pods.facebook.login.FBSDKLoginBehavior;
import org.robovm.pods.facebook.login.FBSDKLoginManager;
import org.robovm.pods.facebook.login.FBSDKLoginManagerLoginResult;

import java.util.List;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookAccessToken;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookVars;
import de.tomgrill.gdxfacebook.core.GraphError;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.core.SignInResult;

public class IOSGDXFacebook extends GDXFacebook {

	private FBSDKLoginManager loginManager;
	private SignInMode signInMode;

	public IOSGDXFacebook(GDXFacebookConfig config) {
		super(config);

		loginManager = new FBSDKLoginManager();
		loginManager.setLoginBehavior(FBSDKLoginBehavior.Native);
	}

	@Override
	public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback) {
		this.callback = callback;
		this.permissions = permissions;
		this.signInMode = mode;

		loadAccessToken();

		if (accessToken == null && FBSDKAccessToken.getCurrentAccessToken() != null) {
			accessToken = new GDXFacebookAccessToken(FBSDKAccessToken.getCurrentAccessToken().getTokenString(), FBSDKAccessToken.getCurrentAccessToken().getExpirationDate().toDate().getTime());
		}


		if (accessToken != null) {
			startSilentSignIn();
		} else {
			startGUISignIn();
		}
	}

	@Override
	public void signOut() {
		super.signOut();
		loginManager.logOut();
	}

	@Override
	protected void startGUISignIn() {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting GUI sign in.");

		VoidBlock2<FBSDKLoginManagerLoginResult, NSError> result = new VoidBlock2<FBSDKLoginManagerLoginResult, NSError>() {

			@Override
			public void invoke(FBSDKLoginManagerLoginResult loginResult, NSError nsError) {
				if (nsError != null) {
					signOut();
					Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Error while trying to sign in: " + nsError.getLocalizedDescription());
					callback.onError(new GraphError(nsError.getLocalizedDescription()));
				} else if (loginResult.isCancelled()) {
					Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign cancelled by user.");
					signOut();
					callback.onCancel();
				} else {
					accessToken = new GDXFacebookAccessToken(FBSDKAccessToken.getCurrentAccessToken().getTokenString(), FBSDKAccessToken.getCurrentAccessToken().getExpirationDate().toDate().getTime());
					storeNewToken(accessToken);
					Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign successful. User token: " + accessToken.getToken());
					callback.onSuccess(new SignInResult(accessToken, "Sign in successful."));
				}

			}

		};

		List<String> listPermissions = (List<String>) permissions;
		if (this.signInMode == SignInMode.PUBLISH) {
			loginManager.logInWithPublishPermissions(listPermissions, result);
		} else {
			loginManager.logInWithReadPermissions(listPermissions, result);
		}


	}

}

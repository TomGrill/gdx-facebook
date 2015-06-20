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

import java.util.Collection;
import java.util.List;

import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.facebook.core.FBSDKAccessToken;
import org.robovm.pods.facebook.login.FBSDKLoginBehavior;
import org.robovm.pods.facebook.login.FBSDKLoginManager;
import org.robovm.pods.facebook.login.FBSDKLoginManagerLoginResult;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.core.GDXFacebookLoginResult;

public class IOSGDXFacebook extends GDXFacebook {

	private GDXFacebookConfig config;

	private FBSDKLoginManager loginManager;

	public IOSGDXFacebook(GDXFacebookConfig config) {
		this.config = config;

		loginManager = new FBSDKLoginManager();
		loginManager.setLoginBehavior(FBSDKLoginBehavior.Native);
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
		if (FBSDKAccessToken.getCurrentAccessToken() != null) {

			GDXFacebookLoginResult result = new GDXFacebookLoginResult();
			result.setAccessToken(FBSDKAccessToken.getCurrentAccessToken().getTokenString());
			callback.onSuccess(result);

		} else {

			VoidBlock2<FBSDKLoginManagerLoginResult, NSError> bloack = new VoidBlock2<FBSDKLoginManagerLoginResult, NSError>() {

				@Override
				public void invoke(FBSDKLoginManagerLoginResult loginResult, NSError nsError) {

					if (nsError != null) {
						GDXFacebookError error = new GDXFacebookError();
						error.setErrorMessage(nsError.getLocalizedDescription());
						callback.onError(error);

					} else if (loginResult.isCancelled()) {
						callback.onCancel();
					} else {
						GDXFacebookLoginResult result = new GDXFacebookLoginResult();
						result.setAccessToken(FBSDKAccessToken.getCurrentAccessToken().getTokenString());
						callback.onSuccess(result);
					}

				}

			};

			List<String> listPermissions = (List<String>) permissions;

			if (withPublishPermissions) {
				loginManager.logInWithPublishPermissions(listPermissions, bloack);
			} else {
				loginManager.logInWithReadPermissions(listPermissions, bloack);
			}
		}

	}

	@Override
	public boolean isLoggedIn() {
		return FBSDKAccessToken.getCurrentAccessToken() != null;
	}

	@Override
	public void logOut() {
		loginManager.logOut();
	}

	@Override
	public String getAccessToken() {
		return FBSDKAccessToken.getCurrentAccessToken().getTokenString();
	}

}

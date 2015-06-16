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

import java.util.List;

import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.facebook.core.FBSDKAccessToken;
import org.robovm.pods.facebook.login.FBSDKLoginBehavior;
import org.robovm.pods.facebook.login.FBSDKLoginManager;
import org.robovm.pods.facebook.login.FBSDKLoginManagerLoginResult;

import de.tomgrill.gdxfacebook.core.FacebookAPI;
import de.tomgrill.gdxfacebook.core.FacebookConfig;
import de.tomgrill.gdxfacebook.core.FacebookUtils;
import de.tomgrill.gdxfacebook.core.ResponseError;
import de.tomgrill.gdxfacebook.core.ResponseListener;

public class IOSFacebookAPI extends FacebookAPI {

	// private UIWindow window;

	// private UIViewController rootViewController;

	private FacebookConfig config;

	private FBSDKLoginManager fbLoginMngr;

	public IOSFacebookAPI(FacebookConfig config) {
		this.config = config;

		fbLoginMngr = new FBSDKLoginManager();

	}

	@Override
	public void signout() {
		isSignedin = false;
		fbLoginMngr.logOut();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void signin(final boolean allowGUI, final ResponseListener responseListener) {

		isSignedin = false;

		if (FBSDKAccessToken.getCurrentAccessToken() != null) {
			isSignedin = true;
			setAccessToken(FBSDKAccessToken.getCurrentAccessToken().getTokenString());
			responseListener.success();
		} else {
			FBSDKLoginBehavior loginBehavior = FBSDKLoginBehavior.Native;

			fbLoginMngr.setLoginBehavior(loginBehavior);

			fbLoginMngr.logInWithReadPermissions((List<String>) FacebookUtils.permissionSplitToList(config.PERMISSIONS), new VoidBlock2<FBSDKLoginManagerLoginResult, NSError>() {

				@Override
				public void invoke(FBSDKLoginManagerLoginResult result, NSError error) {

					if (error != null) {

						ResponseError rError = new ResponseError();
						rError.setCode(-1);
						rError.setMessage("Could not (re)open Facebook session.");
						responseListener.error(rError);

					} else if (result.isCancelled()) {
						responseListener.cancel();
					} else {
						isSignedin = true;
						setAccessToken(result.getToken().getTokenString());
						responseListener.success();
					}

				}

			});
		}

	}

	@Override
	public boolean isLoaded() {
		return true;
	}

}

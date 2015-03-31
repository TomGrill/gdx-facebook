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

import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.bindings.facebook.manager.FacebookError;
import org.robovm.bindings.facebook.manager.FacebookLoginListener;
import org.robovm.bindings.facebook.manager.FacebookManager;
import org.robovm.bindings.facebook.manager.FacebookPermission;
import org.robovm.bindings.facebook.manager.request.GraphUser;

import de.tomgrill.gdxfacebook.core.FacebookAPI;
import de.tomgrill.gdxfacebook.core.FacebookConfig;
import de.tomgrill.gdxfacebook.core.ResponseListener;

public class IOSFacebookAPI extends FacebookAPI {

	private UIWindow window;

	private UIViewController rootViewController;

	public IOSFacebookAPI(FacebookConfig config) {
	}

	@Override
	public void signin(final boolean allowGUI, final ResponseListener responseListener) {

		if (rootViewController == null) {
			rootViewController = new UIViewController();
		}

		if (window == null) {
			window = new UIWindow(UIScreen.getMainScreen().getApplicationFrame());
			window.setRootViewController(rootViewController);
			window.addSubview(rootViewController.getView());
		}

		window.makeKeyAndVisible();

		FacebookPermission[] permissions = new FacebookPermission[] { FacebookPermission.PUBLIC_PROFILE, FacebookPermission.USER_FRIENDS, FacebookPermission.EMAIL };
		// Silently attempt to login the user. This will never show any UI.
		FacebookManager.getInstance().login(permissions, allowGUI, new FacebookLoginListener() {
			@Override
			public void onSuccess(GraphUser result) {
				isSignedin = true;
				responseListener.success();
			}

			@Override
			public void onError(FacebookError error) {
				window.setHidden(true);
				// We ignore any errors because this is a silent login.
				System.out.println("error" + error.getMessage());
			}

			@Override
			public void onCancel() {
				window.setHidden(true);
				System.out.println("cancel");
			}
		});

	}

}

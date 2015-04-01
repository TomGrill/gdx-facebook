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
import org.robovm.bindings.facebook.session.FBSession;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;

import de.tomgrill.gdxfacebook.core.FacebookAPI;
import de.tomgrill.gdxfacebook.core.FacebookConfig;
import de.tomgrill.gdxfacebook.core.FacebookUtils;
import de.tomgrill.gdxfacebook.core.ResponseError;
import de.tomgrill.gdxfacebook.core.ResponseListener;

public class IOSFacebookAPI extends FacebookAPI {

	//private UIWindow window;

	//private UIViewController rootViewController;

	private FacebookConfig config;

	

	private FacebookPermission[] permissions;

	

	public IOSFacebookAPI(FacebookConfig config) {
		this.config = config;


		String[] permissionsSplitted = FacebookUtils.permissionSplit(this.config.PERMISSIONS);
		permissions = new FacebookPermission[permissionsSplitted.length];

		for (int i = 0; i < permissionsSplitted.length; i++) {
			permissions[i] = FacebookPermission.fromName(permissionsSplitted[i]);

		}
/*
		if (rootViewController == null) {
			rootViewController = new UIViewController();
		}

		if (window == null) {
			window = new UIWindow(UIScreen.getMainScreen().getApplicationFrame());
			window.setRootViewController(rootViewController);
			window.addSubview(rootViewController.getView());
		}

		*/
		

	}

	@Override
	public void signout() {
		isSignedin = false;
		// FBSession.getActiveSession().close(true);

		if (FacebookManager.getInstance().isLoggedIn()) {
			FacebookManager.getInstance().logout();
			// Remove user data.
			

		}

	}

	@Override
	public void signin(final boolean allowGUI, final ResponseListener responseListener) {

		isSignedin = false;

		// try silent login first

		// addStrongRef(window);

		//System.out.println("sign in command recevied1");
		
		//window.makeKeyAndVisible();

		FacebookManager.getInstance().login(permissions, allowGUI, new FacebookLoginListener() {
			@Override
			public void onSuccess(GraphUser result) {
				// Login was successful!
				// sampleViewController.setMe(result);
				// Update UI
				// sampleViewController.getTableView().reloadData();
				
				isSignedin = true;

				setAccessToken(FBSession.getActiveSession().getAccessTokenData().getAccessToken());

				responseListener.success();

				//window.setHidden(true);

			}

			@Override
			public void onError(FacebookError error) {
				// We ignore any errors because this is a silent login.
				//System.out.println("error" + error.getMessage());

				ResponseError rError = new ResponseError();
				rError.setCode(-1);
				rError.setMessage(error.getMessage());

				rError.setShouldNotifyUser(error.shouldNotify());

				// TODO write doc for should notify stuff

				responseListener.error(rError);

				// if (allowGUI)
				//window.setHidden(true);
			}

			@Override
			public void onCancel() {

				//System.out.println("cancel");

				responseListener.cancel();
				// if (allowGUI)
				//window.setHidden(true);
			}
		});

		/*
		 * FacebookManager.getInstance().login(permissions, false, new
		 * FacebookLoginListener() {
		 * 
		 * @Override public void onSuccess(GraphUser result) {
		 * Gdx.app.debug("FB", "1");
		 * 
		 * 
		 * isSignedin = true;
		 * 
		 * setAccessToken(FBSession.getActiveSession().getAccessTokenData().
		 * getAccessToken());
		 * 
		 * 
		 * responseListener.success();
		 * 
		 * //window.setHidden(true); }
		 * 
		 * @Override public void onError(FacebookError error) {
		 * Gdx.app.debug("FB", "2"); //window.setHidden(true); // We ignore any
		 * errors because this is a silent login.
		 * System.out.println("error silent" + error.getMessage());
		 * 
		 * openGUILogin(permissions, allowGUI, responseListener);
		 * 
		 * 
		 * }
		 * 
		 * @Override public void onCancel() {
		 * 
		 * Gdx.app.debug("FB", "3"); //window.setHidden(true);
		 * System.out.println("cancel silent");
		 * 
		 * openGUILogin(permissions, allowGUI, responseListener);
		 * 
		 * //responseListener.cancel(); } });
		 * 
		 * 
		 * 
		 * }
		 * 
		 * private void openGUILogin(FacebookPermission[] permissions, final
		 * boolean allowGUI, final ResponseListener responseListener) {
		 * System.out.println("sign in command recevied2"); Gdx.app.debug("FB",
		 * "yooasdf"); if(allowGUI) {
		 * 
		 * 
		 * window.makeKeyAndVisible(); //isSignedin = false;
		 * FacebookManager.getInstance().login(permissions, true, new
		 * FacebookLoginListener() {
		 * 
		 * @Override public void onSuccess(GraphUser result) { isSignedin =
		 * true;
		 * setAccessToken(FBSession.getActiveSession().getAccessTokenData()
		 * .getAccessToken());
		 * 
		 * System.out.println("Toek "+
		 * FBSession.getActiveSession().getAccessTokenData().getAccessToken());
		 * System.out.println("success GUI: " + getAccessToken());
		 * responseListener.success();
		 * 
		 * window.setHidden(true); }
		 * 
		 * @Override public void onError(FacebookError error) {
		 * 
		 * // We ignore any errors because this is a silent login.
		 * System.out.println("error GUI" + error.getMessage());
		 * //responseListener.error(error.getMessage()));
		 * 
		 * ResponseError error2 = new ResponseError(); error2.setCode(-1);
		 * error2.setMessage(error2.getMessage());
		 * responseListener.error(error2);
		 * 
		 * window.setHidden(true); }
		 * 
		 * @Override public void onCancel() {
		 * 
		 * 
		 * 
		 * if(FBSession.getActiveSession().isOpen()) { isSignedin = true;
		 * setAccessToken
		 * (FBSession.getActiveSession().getAccessTokenData().getAccessToken());
		 * System.out.println("strange succi GUI"); System.out.println("Toek "+
		 * FBSession.getActiveSession().getAccessTokenData().getAccessToken());
		 * System.out.println("success GUI: " + getAccessToken());
		 * responseListener.success(); } else {
		 * System.out.println("cancel GUI"); responseListener.cancel(); }
		 * 
		 * 
		 * window.setHidden(true);
		 * 
		 * 
		 * } }); } else {
		 * 
		 * System.out.println("no GUI login allowed"); window.setHidden(true); }
		 */
	}

	@Override
	public boolean isLoaded() {
		return true;
	}

}

package de.tpronold.gdxfacebook.ios;

import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.bindings.facebook.manager.FacebookError;
import org.robovm.bindings.facebook.manager.FacebookLoginListener;
import org.robovm.bindings.facebook.manager.FacebookManager;
import org.robovm.bindings.facebook.manager.FacebookPermission;
import org.robovm.bindings.facebook.manager.request.GraphUser;

import de.tpronold.gdxfacebook.core.FacebookAPI;
import de.tpronold.gdxfacebook.core.FacebookConfig;
import de.tpronold.gdxfacebook.core.ResponseListener;

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

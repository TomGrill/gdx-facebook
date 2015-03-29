package de.tomgrill.gdxfacebook.android;

import android.app.Activity;

import com.facebook.Session;
import com.facebook.SessionState;

import de.tomgrill.gdxfacebook.core.FacebookAPI;
import de.tomgrill.gdxfacebook.core.FacebookConfig;
import de.tomgrill.gdxfacebook.core.FacebookUtils;
import de.tomgrill.gdxfacebook.core.ResponseError;
import de.tomgrill.gdxfacebook.core.ResponseListener;

public class AndroidFacebookAPI extends FacebookAPI {

	private final Activity activity;
	private final FacebookConfig config;

	public AndroidFacebookAPI(final Activity activity, final FacebookConfig config) {
		this.activity = activity;
		this.config = config;
	}

	@Override
	public boolean isSignedin() {
		return isSignedin;
	}

	@Override
	public void signout() {
		super.signout();
		isSignedin = false;
		Session.getActiveSession().closeAndClearTokenInformation();
	}

	@Override
	public void signin(final boolean allowGUI, final ResponseListener responseListener) {
		isSignedin = false;

		Session.openActiveSession(activity, allowGUI, FacebookUtils.permissionSplitToList(config.PERMISSIONS), new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state, Exception exception) {

				if (!session.isOpened() || exception != null || state == null || state.isClosed()) {
					ResponseError error = new ResponseError();
					error.setCode(-1);
					error.setMessage("Could not (re)open Facebook session.");
					responseListener.error(error);
				} else {
					isSignedin = true;
					accessToken = session.getAccessToken();
					responseListener.success();
				}

			}
		});

	}

	@Override
	public void setAccessToken(String accessToken) {
		// super.setAccessToken(accessToken);

		// Session session = Session.getActiveSession();
		// if (session != null) {
		// session.open(AccessToken.createFromExistingAccessToken(accessToken,
		// null, null, null, null), new StatusCallback() {
		//
		// @Override
		// public void call(Session session, SessionState state, Exception
		// exception) {
		//
		// }
		// });
		// }
	}

}

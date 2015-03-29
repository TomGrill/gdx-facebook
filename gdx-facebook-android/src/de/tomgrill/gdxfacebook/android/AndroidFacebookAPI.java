package de.tomgrill.gdxfacebook.android;

import android.app.Activity;

import com.facebook.Session;
import com.facebook.Session.OpenRequest;
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
		signout();
		OpenRequest openRequest = new OpenRequest(activity).setPermissions(FacebookUtils.permissionSplit(config.PERMISSIONS)).setCallback(new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state, Exception exception) {

				if (!session.isOpened() || exception != null || state == null || state.isClosed()) {
					ResponseError error = new ResponseError();
					error.setCode(-1);
					error.setMessage("Could not (re)open Facebook session.");
					responseListener.error(error);
				} else {
					isSignedin = true;
					setAccessToken(session.getAccessToken());
					responseListener.success();
				}

			}
		});

		Session session = new Session.Builder(activity).build();
		if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) || allowGUI) {
			Session.setActiveSession(session);
			session.openForRead(openRequest);
		}

	}

	@Override
	public void setAccessToken(String accessToken) {
		super.setAccessToken(accessToken);

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

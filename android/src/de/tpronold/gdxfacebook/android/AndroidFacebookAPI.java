package de.tpronold.gdxfacebook.android;

import android.app.Activity;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import de.tpronold.gdxfacebook.core.FacebookAPI;
import de.tpronold.gdxfacebook.core.FacebookConfig;
import de.tpronold.gdxfacebook.core.ResponseError;
import de.tpronold.gdxfacebook.core.ResponseListener;

public class AndroidFacebookAPI implements FacebookAPI {

	private final Activity activity;
	private final FacebookConfig config;

	private boolean isSignedin = false;

	public AndroidFacebookAPI(final Activity activity, final FacebookConfig config) {
		this.activity = activity;
		this.config = config;
	}

	@Override
	public void signin(final ResponseListener responseListener) {
		signin(true, responseListener);

	}

	@Override
	public void signin(boolean allowGUI, final ResponseListener responseListener) {

		isSignedin = false;

		Session.openActiveSession(activity, allowGUI, new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state, Exception exception) {

				if (!session.isOpened() || exception != null || state == null || state.isClosed()) {
					ResponseError error = new ResponseError();
					error.setCode(-1);
					error.setMessage("Could not (re)open Facebook session.");
					responseListener.error(error);
				} else {
					isSignedin = true;
					responseListener.success();
				}

			}
		});
	}

	@Override
	public boolean isSignedin() {
		return isSignedin;
	}

	@Override
	public void signout() {
		isSignedin = false;
		Session.getActiveSession().closeAndClearTokenInformation();
	}

	@Override
	public void graphUser(final ResponseListener responseListener) {
		if (isSignedin) {
			Request.newMeRequest(Session.getActiveSession(), new Request.GraphUserCallback() {

				@Override
				public void onCompleted(GraphUser user, Response response) {
					FacebookRequestError fberror = response.getError();

					if (fberror == null) {
						// TODO add code to return GraphUser
						user.getBirthday();
					} else {
						ResponseError error = new ResponseError();
						error.setCode(fberror.getErrorCode());
						error.setMessage(fberror.getErrorMessage());
						error.setType(fberror.getErrorType());
						error.setUserMessage(fberror.getErrorUserMessage());
						error.setUserTitle(fberror.getErrorUserTitle());
						error.setShouldNotifyUser(fberror.shouldNotifyUser());
						responseListener.error(error);
					}

				}
			}).executeAsync();
		} else {
			ResponseError error = new ResponseError();
			error.setCode(110);
			error.setMessage("You are not signed in.");
			responseListener.error(error);
		}
	}

}

package de.tpronold.gdxfacebook.core.tests.stubs;

import de.tpronold.gdxfacebook.core.FacebookAPI;
import de.tpronold.gdxfacebook.core.ResponseListener;

public class FacebookAPIStub implements FacebookAPI {

	@Override
	public void signin(ResponseListener responseListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSignedin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void signout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void signin(boolean silentLogin, ResponseListener responseListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void graphUser(ResponseListener responseListener) {
		// TODO Auto-generated method stub

	}

}

package de.tpronold.gdxfacebook.core.tests.stubs;

import de.tpronold.gdxfacebook.core.FacebookAPI;
import de.tpronold.gdxfacebook.core.ResponseListener;

public class FacebookAPIStub extends FacebookAPI {

	@Override
	public boolean isSignedin() {
		return false;
	}

	@Override
	public void signout() {

	}

	@Override
	public void signin(boolean allowGUI, ResponseListener responseListener) {
		// TODO Auto-generated method stub

	}

}

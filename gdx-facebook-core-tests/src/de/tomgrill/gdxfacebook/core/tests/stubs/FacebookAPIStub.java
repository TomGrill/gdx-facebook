package de.tomgrill.gdxfacebook.core.tests.stubs;

import de.tomgrill.gdxfacebook.core.FacebookAPI;
import de.tomgrill.gdxfacebook.core.ResponseListener;

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

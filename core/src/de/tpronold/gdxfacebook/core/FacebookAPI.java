package de.tpronold.gdxfacebook.core;

public interface FacebookAPI {

	public void signin(ResponseListener responseListener);

	public boolean isSignedin();

	public void signout();
}

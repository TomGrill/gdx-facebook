package de.tpronold.gdxfacebook.core;

public interface FacebookAPI {

	public void signin(ResponseListener responseListener);

	public void signin(boolean silentLogin, ResponseListener responseListener);

	public boolean isSignedin();

	public void signout();

	public void graphUser(ResponseListener responseListener);
}

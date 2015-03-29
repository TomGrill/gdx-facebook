package de.tomgrill.gdxfacebook.core;

public interface FacebookLoginListener {
	public void onSuccess(String accessToken, long expires);

	public void onError(String error, String errorCode, String errorDescription, String errorReason);

	public void onCancel();
}

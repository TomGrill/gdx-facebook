package de.tomgrill.gdxfacebook.core;

public interface ResponseListener {
	public void error(ResponseError responseError);

	public void success();

	public void cancel();
}

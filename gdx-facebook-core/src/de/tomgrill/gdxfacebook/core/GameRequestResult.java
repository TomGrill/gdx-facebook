package de.tomgrill.gdxfacebook.core;

import com.badlogic.gdx.utils.Array;

public class GameRequestResult extends Result {

	private String requestId;
	private Array<String> recipients;

	public GameRequestResult(String requestId, Array<String> recipients) {
		super("Game Request Result");
		this.recipients = recipients;
		this.requestId = requestId;
	}

	public Array<String> getRecipients() {
		return recipients;
	}

	public String getRequestId() {
		return requestId;
	}
}

package de.tpronold.gdxfacebook.core;

public class ResponseError {

	// TODO colver all from FacebookRequestError.class
	private int code = 0;
	private String message = "No error message set.";
	private String type = "";
	private String userMessage = "";
	private String userTitle = "";

	private boolean shouldNotifyUser = false;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setShouldNotifyUser(boolean shouldNotifyUser) {
		this.shouldNotifyUser = shouldNotifyUser;
	}

	public boolean shouldNotifyUser() {
		return this.shouldNotifyUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

}

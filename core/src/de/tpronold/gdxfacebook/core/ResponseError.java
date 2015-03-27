package de.tpronold.gdxfacebook.core;

public class ResponseError {

	public static final int EC_CANNOT_RESTORE_SESSION = -112;
	public static final int EC_EMPTY_ACCESS_TOKEN = -110;
	public static final int EC_CANCELED = -117;
	public static final int EC_FAILED = -118;
	public static final int EC_OK = 200;
	public static final int EC_BAD_REQUEST = 400;

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

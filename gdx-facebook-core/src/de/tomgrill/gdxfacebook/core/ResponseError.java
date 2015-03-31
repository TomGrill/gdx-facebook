/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tomgrill.gdxfacebook.core;

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

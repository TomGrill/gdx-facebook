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

public class GDXFacebookError {

	private String errorMessage;
	private String errorType;
	private String errorCode;
	private String errorSubCode;
	private String errorUserTitle;
	private String errorUserMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorSubCode() {
		return errorSubCode;
	}

	public void setErrorSubCode(String errorSubCode) {
		this.errorSubCode = errorSubCode;
	}

	public String getErrorUserTitle() {
		return errorUserTitle;
	}

	public void setErrorUserTitle(String errorUserTitle) {
		this.errorUserTitle = errorUserTitle;
	}

	public String getErrorUserMessage() {
		return errorUserMessage;
	}

	public void setErrorUserMessage(String errorUserMessage) {
		this.errorUserMessage = errorUserMessage;
	}

}

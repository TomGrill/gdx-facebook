/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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

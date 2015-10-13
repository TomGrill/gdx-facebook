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

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class JsonResult extends Result {

	protected JsonValue jsonValue;

	private JsonReader jsonReader = new JsonReader();

	public JsonResult(JsonValue jsonValue) {
		super(jsonValue.toString());
		this.jsonValue = jsonValue;
	}

	public JsonResult(String jsonString) {
		super(jsonString);
		setJsonString(jsonString);
	}

	public JsonValue getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(JsonValue jsonValue) {
		this.jsonValue = jsonValue;
	}

	public void setJsonString(String jsonString) {
		this.jsonValue = jsonReader.parse(jsonString);
	}
}

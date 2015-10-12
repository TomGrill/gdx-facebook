package de.tomgrill.gdxfacebook.core;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class JsonResult extends Result {

    protected JsonValue jsonValue;

    private JsonReader jsonReader = new JsonReader();

    public JsonResult(JsonValue jsonValue) {
        super(jsonValue.asString());
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

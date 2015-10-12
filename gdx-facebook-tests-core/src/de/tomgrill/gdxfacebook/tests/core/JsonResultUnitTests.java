package de.tomgrill.gdxfacebook.tests.core;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

import org.junit.Before;
import org.junit.Test;

import de.tomgrill.gdxfacebook.core.JsonResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonResultUnitTests {
    JsonResult fixture;

    @Before
    public void setup() {
        JsonReader writer = new JsonReader();
        JsonValue value = writer.parse("{testValue:1}");
        fixture = new JsonResult(value);
    }

    @Test
    public void setGetJsonValue() {
        JsonReader writer = new JsonReader();
        JsonValue value = writer.parse("{testValue:2}");
        fixture.setJsonValue(value);
        assertEquals(value, fixture.getJsonValue());
    }

    @Test
    public void constructorWithJsonValue() {
        JsonReader writer = new JsonReader();
        JsonValue value = writer.parse("{testValue:5}");
        fixture = new JsonResult(value);
        assertEquals(value, fixture.getJsonValue());

        assertTrue(fixture.getJsonValue().has("testValue"));
        assertEquals(5, fixture.getJsonValue().getInt("testValue"));
    }

    @Test(expected = SerializationException.class)
    public void setJsonStringThrows() {
        fixture.setJsonString("INVALID_JSON_STRING{}");
    }

    @Test(expected = SerializationException.class)
    public void jsonStringConstructorThrows() {
        new JsonResult("INVALID_JSON_STRING{}");
    }
}

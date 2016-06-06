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

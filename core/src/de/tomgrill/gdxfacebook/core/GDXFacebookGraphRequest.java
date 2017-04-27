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

import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Build a proper Facebook Graph API request with this class.
 *
 * @author Thomas Pronold (TomGrill) mail@tomgrill.de *
 * @see <a href="https://developers.facebook.com/docs/graph-api/using-graph-api/">https://developers.facebook.com/docs/graph-api/using-graph-api/</a>
 */
public class GDXFacebookGraphRequest extends AbstractRequest {


    public static String defaultEncoding = "UTF-8";
    public static String nameValueSeparator = "=";
    public static String parameterSeparator = "&";


    private static String encode(String content, String encoding) {
        try {
            return URLEncoder.encode(content, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public final ArrayMap<String, String> getHeaders() {
        return null;
    }

    public String getContent() {
        StringBuffer convertedParameters = new StringBuffer();

        for (ObjectMap.Entry<String, String> entry : fields) {
            convertedParameters.append(encode(entry.key, defaultEncoding));
            convertedParameters.append(nameValueSeparator);
            convertedParameters.append(encode(entry.value, defaultEncoding));
            convertedParameters.append(parameterSeparator);
        }

        if (convertedParameters.length() > 0)
            convertedParameters.deleteCharAt(convertedParameters.length() - 1);
        return convertedParameters.toString();

    }


    public String getJavascriptObjectString() {
        StringBuffer convertedParameters = new StringBuffer();

        for (ObjectMap.Entry<String, String> entry : fields) {
            convertedParameters.append(entry.key);
            convertedParameters.append(":\"");
            convertedParameters.append(entry.value.replace("\"", "\\\""));
            convertedParameters.append("\",");
        }
        if (convertedParameters.length() > 0)
            convertedParameters.deleteCharAt(convertedParameters.length() - 1);


        return convertedParameters.toString();
    }

    @Override
    public final InputStream getContentStream() throws IOException {
        return null;
    }

    @Override
    public GDXFacebookGraphRequest useCurrentAccessToken() {
        return (GDXFacebookGraphRequest) super.useCurrentAccessToken();
    }

    @Override
    public GDXFacebookGraphRequest setNode(String node) {
        return (GDXFacebookGraphRequest) super.setNode(node);
    }

    @Override
    public GDXFacebookGraphRequest setMethod(String method) {
        return (GDXFacebookGraphRequest) super.setMethod(method);
    }

    @Override
    public GDXFacebookGraphRequest setTimeout(int timeout) {
        return (GDXFacebookGraphRequest) super.setTimeout(timeout);
    }

    @Override
    public GDXFacebookGraphRequest putField(String key, String value) {
        return (GDXFacebookGraphRequest) super.putField(key, value);
    }

    @Override
    public GDXFacebookGraphRequest putFields(ArrayMap<String, String> fields) {
        return (GDXFacebookGraphRequest) super.putFields(fields);
    }
}

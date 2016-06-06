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

import java.io.IOException;
import java.io.InputStream;

public interface Request {

    String getNode();

    /**
     * Sets the node. F.e. "me", "bgolub" @see <a
     * href="https://developers.facebook.com/docs/graph-api/reference"
     * >https://developers.facebook.com/docs/graph-api/reference</a>
     *
     * @param node
     */
    Request setNode(String node);


    String getUrl();

    /**
     * Call this method when your request requires an access_token field. The
     * field will be set with the current available access token.
     *
     * @return this
     */
    Request useCurrentAccessToken();

    boolean isUseCurrentAccessToken();

    String getMethod();

    Request setMethod(String method);

    int getTimeout();

    Request setTimeout(int timeout);

    ArrayMap<String, String> getHeaders();

    String getContent();

    InputStream getContentStream() throws IOException;

    AbstractRequest putField(String key, String value);

    AbstractRequest putFields(ArrayMap<String, String> fields);

}

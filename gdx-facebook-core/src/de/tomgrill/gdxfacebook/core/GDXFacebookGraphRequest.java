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

import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Build a proper Facebook Graph API request with this class.
 *
 * @author Thomas Pronold (TomGrill) mail@tomgrill.de
 * @see <a
 * href="https://developers.facebook.com/docs/graph-api/using-graph-api/">https://developers.facebook.com/docs/graph-api/using-graph-api/</a>
 * for more information on how Facebook Graph API works.
 */
public class GDXFacebookGraphRequest {

    private String url = "https://graph.facebook.com/";

    private String node = null;

    private ArrayMap<String, String> fields;

    private boolean useCurrentAccessToken = false;

    private String method = HttpMethods.GET;

    private int timeout = 10000;

    public GDXFacebookGraphRequest() {
        fields = new ArrayMap<String, String>();
    }

    /**
     * Sets the node. F.e. "me", "bgolub" @see <a
     * href="https://developers.facebook.com/docs/graph-api/reference"
     * >https://developers.facebook.com/docs/graph-api/reference</a>
     *
     * @param node
     */
    public GDXFacebookGraphRequest setNode(String node) {

        this.node = node.trim();
        if (this.node.startsWith("/")) {
            this.node.replaceFirst("/", "");
        }

        return this;
    }

    /**
     * Add a field to the request.
     *
     * @param key
     * @param value
     * @return this
     */
    public GDXFacebookGraphRequest putField(String key, String value) {
        fields.put(key, value);
        return this;
    }

    /**
     * Add multiple fields to the request.
     *
     * @param fields
     * @return this
     */
    public GDXFacebookGraphRequest putFields(ArrayMap<String, String> fields) {
        fields.putAll(fields);
        return this;
    }

    /**
     * Call this method when your request requires an access_token field. The
     * field will be set with the current available access token.
     *
     * @return this
     */
    public GDXFacebookGraphRequest useCurrentAccessToken() {
        this.useCurrentAccessToken = true;
        return this;
    }

    protected boolean isUseCurrentAccessToken() {
        return useCurrentAccessToken;
    }

    protected String getUrl() {
        return url;
    }

    protected String getNode() {
        return node;
    }

    protected String getMethod() {
        return this.method;
    }

    /**
     * Set the HTTP request method. Default is GET
     *
     * @param method
     */
    public GDXFacebookGraphRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    protected String getContentAsString() {
        String content = "";

        for (int i = 0; i < fields.size; i++) {
            content += fields.getKeyAt(i) + "=" + fields.getValueAt(i);
            if (i + 1 < fields.size) {
                content += "&";
            }
        }
        return content;
    }

    protected int getTimeout() {
        return timeout;
    }

    /**
     * Set the timeout in msec. Default is 10000 ~ 10 seconds
     *
     * @param timeout
     */
    public GDXFacebookGraphRequest setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
}

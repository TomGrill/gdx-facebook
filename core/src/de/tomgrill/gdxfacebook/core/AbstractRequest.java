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

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.ArrayMap;

public abstract class AbstractRequest implements Request {

    protected String node = "";

    protected String url = "https://graph.facebook.com/";

    protected boolean useCurrentAccessToken = false;

    private String method = Net.HttpMethods.GET;

    private int timeout = 10000;

    protected ArrayMap<String, String> fields = new ArrayMap<String, String>();

    @Override
    public AbstractRequest useCurrentAccessToken() {
        this.useCurrentAccessToken = true;
        return this;
    }
    @Override
    public boolean isUseCurrentAccessToken() {
        return useCurrentAccessToken;
    }

    @Override
    public AbstractRequest setNode(String node) {

        this.node = node.trim();
        if (this.node.startsWith("/")) {
            this.node = this.node.replaceFirst("/", "");
        }

        return this;
    }

    @Override
    public String getNode() {
        return node;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    /**
     * Set the HTTP request method. Default is GET
     *
     * @param method HTTP method (POST,GET,..)
     */
    @Override
    public AbstractRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    /**
     * Set the timeout in msec. Default is 10000 ~ 10 seconds
     *
     * @param timeout
     */
    @Override
    public AbstractRequest setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Add a field to the request.
     *
     * @param key
     * @param value
     * @return this
     */
    public AbstractRequest putField(String key, String value) {
        fields.put(key, value);
        return this;
    }

    /**
     * Add multiple fields to the request.
     *
     * @param fields
     * @return this
     */
    public AbstractRequest putFields(ArrayMap<String, String> fields) {
        fields.putAll(fields);
        return this;
    }
}

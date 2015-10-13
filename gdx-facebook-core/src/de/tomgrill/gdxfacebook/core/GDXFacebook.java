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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.Array;


public abstract class GDXFacebook {

    protected GDXFacebookConfig config;

    public GDXFacebook(GDXFacebookConfig config) {
        this.config = config;
    }

    abstract public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback);

    /**
     * Currently used accessToken. Null if user is not signed in.
     *
     * @return accessToken
     */
    abstract public AccessToken getAccessToken();

    abstract public void signOut();

    abstract public boolean isSignedIn();

    public void newGraphRequest(GDXFacebookGraphRequest request, final GDXFacebookCallback<JsonResult> callback) {
        String accessToken = null;
        if (getAccessToken() != null) {
            accessToken = getAccessToken().getToken();
        }

        if (request.isUseCurrentAccessToken() && accessToken != null) {
            request.putField("access_token", accessToken);
        }

        Net.HttpRequest httpRequest = new Net.HttpRequest(request.getMethod());
        String url = request.getUrl() + config.GRAPH_API_VERSION + "/" + request.getNode();
        httpRequest.setUrl(url);
        httpRequest.setContent(request.getContentAsString());
        httpRequest.setTimeOut(request.getTimeout());


        HttpRequestBuilder builder = new HttpRequestBuilder().newRequest();
        builder.method(request.getMethod());

        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String resultString = httpResponse.getResultAsString();
                int statusCode = httpResponse.getStatus().getStatusCode();

                if (statusCode == -1) {
                    GraphError error = new GraphError("Connection time out. Consider increasing timeout value by using setTimeout()");
                    callback.onError(error);
                } else if (statusCode >= 200 && statusCode < 300) {
                    callback.onSuccess(new JsonResult(resultString));
                } else {
                    GraphError error = new GraphError("Error: " + resultString);
                    callback.onError(error);
                }
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                callback.onFail(t);
            }

            @Override
            public void cancelled() {
                callback.onCancel();
            }
        });

    }
}

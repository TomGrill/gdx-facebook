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

public class GDXFacebookConfig {

    /**
     * gdx-facebook stores certain values and Facebook tokens in this file. (It's not secured)
     */
    public String PREF_FILENAME = ".gdxFacebookTokenData";

    /**
     * Put you Facebook App ID here. Get it from: https://developers.facebook.com/
     */
    public String APP_ID = "0123456789";

    /**
     * The Graph API version to use for graph request.
     */
    public String GRAPH_API_VERSION = "v3.2";

}

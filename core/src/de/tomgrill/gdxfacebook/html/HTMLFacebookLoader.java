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

package de.tomgrill.gdxfacebook.html;

import de.tomgrill.gdxfacebook.core.FacebookLoader;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;

public class HTMLFacebookLoader implements FacebookLoader {

    private HTMLGDXFacebook htmlgdxFacebook;

    @Override
    public GDXFacebook load(final GDXFacebookConfig config) {

        htmlgdxFacebook = new HTMLGDXFacebook(config);

        JSNIFacebookSDK.initSDK(config.APP_ID, config.GRAPH_API_VERSION, new InitCallback() {
            @Override
            public void loaded() {
                htmlgdxFacebook.loaded = true;
            }
        });

        return htmlgdxFacebook;

    }
}

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

import com.badlogic.gdx.utils.Array;

public class FallbackGDXFacebook extends GDXFacebook {

    public FallbackGDXFacebook(GDXFacebookConfig config) {
        super(config);
    }

    @Override
    public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback callback) {
        callback.onError(new GraphError(GDXFacebookVars.LOG_TAG + " is not installed."));
    }
}

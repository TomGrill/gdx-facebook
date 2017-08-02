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

package de.tomgrill.gdxfacebook.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import de.tomgrill.gdxfacebook.core.FacebookLoader;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;

public class AndroidFacebookLoader implements FacebookLoader {

    @Override
    public GDXFacebook load(GDXFacebookConfig config) {

        if (Gdx.app instanceof AndroidApplication) {
            AndroidApplication androidApplication = (AndroidApplication) Gdx.app;
            AndroidGDXFacebook androidGDXFacebook = new AndroidGDXFacebook(androidApplication, config);
            androidApplication.addAndroidEventListener(androidGDXFacebook);
            return androidGDXFacebook;
        }

        if (Gdx.app instanceof AndroidFragmentApplication) {
            AndroidFragmentApplication androidFragmentApplication = (AndroidFragmentApplication) Gdx.app;
            AndroidGDXFacebook androidGDXFacebook = new AndroidGDXFacebook(androidFragmentApplication, config);
            androidFragmentApplication.addAndroidEventListener(androidGDXFacebook);
            return androidGDXFacebook;
        }

        throw new RuntimeException("gdx-facebook: No support for: " + Gdx.app.getClass().getSimpleName());
    }
}

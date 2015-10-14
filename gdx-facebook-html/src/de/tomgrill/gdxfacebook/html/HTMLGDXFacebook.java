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

package de.tomgrill.gdxfacebook.html;

import com.badlogic.gdx.utils.Array;

import de.tomgrill.gdxfacebook.core.GDXFacebookAccessToken;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookGameRequest;
import de.tomgrill.gdxfacebook.core.GameRequestResult;
import de.tomgrill.gdxfacebook.core.SignInResult;
import de.tomgrill.gdxfacebook.core.SignInMode;

public class HTMLGDXFacebook extends GDXFacebook {
	public HTMLGDXFacebook(GDXFacebookConfig config) {
		super(config);
	}

	@Override
	public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback) {

	}

	@Override
	public void showGameRequest(GDXFacebookGameRequest request, GDXFacebookCallback<GameRequestResult> callback) {

	}

	@Override
	public GDXFacebookAccessToken getAccessToken() {
		return null;
	}

	@Override
	public boolean isSignedIn() {
		return false;
	}

	@Override
	protected void startGUISignIn() {

	}
}

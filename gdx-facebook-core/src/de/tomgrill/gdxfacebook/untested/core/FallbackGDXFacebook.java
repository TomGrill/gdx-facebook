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

package de.tomgrill.gdxfacebook.untested.core;

import java.util.Collection;

public class FallbackGDXFacebook extends GDXFacebook {

	private GDXFacebookError error;

	public FallbackGDXFacebook(final GDXFacebookConfig config) {
		super(config);
		error = new GDXFacebookError();
		error.setErrorMessage("gdx-facebook not installed");
	}

	@Override
	public void loginWithReadPermissions(Collection<String> permissions, GDXFacebookCallback<GDXFacebookLoginResult> callback) {
		callback.onError(error);
	}

	@Override
	public void loginWithPublishPermissions(Collection<String> permissions, GDXFacebookCallback<GDXFacebookLoginResult> callback) {
		callback.onError(error);
	}

	@Override
	public boolean isLoggedIn() {
		return false;
	}

	@Override
	public void logOut() {

	}

	@Override
	public GDXFacebookAccessToken getAccessToken() {
		return null;
	}

	@Override
	public void newGraphRequest(GDXFacebookGraphRequest request, GDXFacebookCallback<GDXFacebookGraphResult> callback) {
		callback.onError(error);
	}

	@Override
	protected GDXFacebookAccessToken loadAccessToken() {
		return null;
	}

	@Override
	protected void storeToken(GDXFacebookAccessToken token) {
	}
}

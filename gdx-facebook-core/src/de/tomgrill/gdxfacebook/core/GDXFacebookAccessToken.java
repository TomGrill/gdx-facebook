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

public class GDXFacebookAccessToken {

	private String token;
	private String userId;
	private String applicationId;

	private Array<String> permissions;
	private Array<String> declinedPermissions;

	private long expirationTime;
	private long lastRefreshTime;

	public GDXFacebookAccessToken() {

	}

	public GDXFacebookAccessToken(String token, String applicationId, String userId, Array<String> permissions, Array<String> declinedPermissions, long expirationTime,
			long lastRefreshTime) {

		setToken(token);
		setUserId(userId);
		setApplicationId(applicationId);
		setPermissions(permissions);
		setDeclinedPermissions(declinedPermissions);
		setExpirationTime(expirationTime);
		setLastRefreshTime(lastRefreshTime);

	}

	public String getToken() {
		return token;
	}

	public void setToken(String accessToken) {
		this.token = accessToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public Array<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Array<String> permissions) {
		this.permissions = permissions;
	}

	public Array<String> getDeclinedPermissions() {
		return declinedPermissions;
	}

	public void setDeclinedPermissions(Array<String> declinedPermissions) {
		this.declinedPermissions = declinedPermissions;
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public long getLastRefreshTime() {
		return lastRefreshTime;
	}

	public void setLastRefreshTime(long lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}
}

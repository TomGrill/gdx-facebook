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

/**
 * Callback interface for graph request.
 * 
 * @author Thomas Pronold (TomGrill) mail@tomgrill.de
 *
 * @param <T>
 */
public interface GDXFacebookCallback<T> {

	/**
	 * Called when the request returned successfully.
	 * 
	 * @param result
	 */
	public void onSuccess(T result);

	/**
	 * Called on error. Causes by a invalid graph request or when making a
	 * request without the required permissions.
	 * 
	 * @param error
	 */
	public void onError(GDXFacebookError error);

	/**
	 * Called when a technical error with the connection occurs.
	 * 
	 * @param t
	 */
	public void onFail(Throwable t);

	/**
	 * Called when the request is canceled by the user or anything else.
	 */
	public void onCancel();

}

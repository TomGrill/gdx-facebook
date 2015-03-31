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

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class FacebookSystem {
	public static final String TAG = "libGDX Facebook";

	private FacebookConfig config;

	private Class<?> gdxClazz = null;
	// private Class<?> gdxLifecycleListenerClazz = null;
	private Object gdxAppObject = null;
	// private Method gdxAppLogMethod = null;
	// private Method gdxAppLogMethodT = null;
	// private Method gdxAppAddLifecycleListenerMethod = null;

	private FacebookAPI facebookAPI = null;

	public FacebookSystem(FacebookConfig config) {
		this.config = config;

		loadGdxReflections();

		tryLoadAndroidFacebookAPI();
		tryLoadIOSFacebookForAPI();
		tryLoadDesktopFacebookForAPI();
		tryLoadHTMLFacebookForAPI();
	}

	private void loadGdxReflections() {

		try {
			gdxClazz = ClassReflection.forName("com.badlogic.gdx.Gdx");
			// gdxLifecycleListenerClazz =
			// ClassReflection.forName("com.badlogic.gdx.LifecycleListener");
			gdxAppObject = ClassReflection.getField(gdxClazz, "app").get(null);
			// gdxAppAddLifecycleListenerMethod =
			// ClassReflection.getMethod(gdxAppObject.getClass(),
			// "addLifecycleListener", gdxLifecycleListenerClazz);

		} catch (ReflectionException e) {
			throw new RuntimeException("No libGDX environment. \n");
		}

	}

	private void tryLoadIOSFacebookForAPI() {

		if (Gdx.app.getType() != ApplicationType.iOS) {
			Gdx.app.debug(TAG, "Skip loading FacebookAPI for iOS. Not running iOS. \n");
			return;
		}
		try {

			final Class<?> facebookClazz = ClassReflection.forName("de.tomgrill.gdxfacebook.ios.IOSFacebookAPI");
			Object facebook = ClassReflection.getConstructor(facebookClazz, FacebookConfig.class).newInstance(config);

			facebookAPI = (FacebookAPI) facebook;

			Gdx.app.debug(TAG, "FacebookAPI for iOS loaded successfully.");

		} catch (ReflectionException e) {
			Gdx.app.debug(TAG, "Error creating FacebookAPI for iOS (are the libGDX Facebook **.jar files installed?). \n");
			e.printStackTrace();
		}

	}

	private void tryLoadDesktopFacebookForAPI() {
		if (Gdx.app.getType() != ApplicationType.Desktop) {
			Gdx.app.debug(TAG, "Skip loading FacebookAPI for Desktop. Not running Desktop. \n");
			return;
		}
		try {

			final Class<?> facebookClazz = ClassReflection.forName("de.tomgrill.gdxfacebook.desktop.DesktopFacebookAPI");
			Object facebook = ClassReflection.getConstructor(facebookClazz, FacebookConfig.class).newInstance(config);

			facebookAPI = (FacebookAPI) facebook;

			Gdx.app.debug(TAG, "FacebookAPI for Desktop loaded successfully.");

		} catch (ReflectionException e) {
			Gdx.app.debug(TAG, "Error creating FacebookAPI for Desktop (are the libGDX Facebook **.jar files installed?). \n");
			e.printStackTrace();
		}

	}

	private void tryLoadHTMLFacebookForAPI() {
		if (Gdx.app.getType() != ApplicationType.WebGL) {
			Gdx.app.debug(TAG, "Skip loading FacebookAPI for HTML. Not running HTML. \n");
			return;
		}

		try {

			final Class<?> facebookClazz = ClassReflection.forName("de.tomgrill.gdxfacebook.html.HTMLFacebookAPI");
			Object facebook = ClassReflection.getConstructor(facebookClazz, FacebookConfig.class).newInstance(config);

			facebookAPI = (FacebookAPI) facebook;

			Gdx.app.debug(TAG, "FacebookAPI for HTML loaded successfully.");

		} catch (ReflectionException e) {
			Gdx.app.debug(TAG, "Error creating FacebookAPI for HTML (are the libGDX Facebook **.jar files installed?). \n");
			e.printStackTrace();
		}

	}

	private void tryLoadAndroidFacebookAPI() {
		if (Gdx.app.getType() != ApplicationType.Android) {
			Gdx.app.debug(TAG, "Skip loading FacebookAPI for Android. Not running Android. \n");
			return;
		}

		try {

			Class<?> activityClazz = ClassReflection.forName("android.app.Activity");
			final Class<?> facebookClazz = ClassReflection.forName("de.tomgrill.gdxfacebook.android.AndroidFacebookAPI");

			Object activity = null;
			if (ClassReflection.isAssignableFrom(activityClazz, gdxAppObject.getClass())) {
				activity = gdxAppObject;
			} else {
				Class<?> supportFragmentClass = findClass("android.support.v4.app.Fragment");
				if (supportFragmentClass != null && ClassReflection.isAssignableFrom(supportFragmentClass, gdxAppObject.getClass())) {
					activity = ClassReflection.getMethod(supportFragmentClass, "getActivity").invoke(gdxAppObject);

				} else {

					Class<?> fragmentClass = findClass("android.app.Fragment");

					if (fragmentClass != null && ClassReflection.isAssignableFrom(fragmentClass, gdxAppObject.getClass())) {
						activity = ClassReflection.getMethod(fragmentClass, "getActivity").invoke(gdxAppObject);
					}
				}
			}

			if (activity == null) {
				throw new RuntimeException("Can't find your gdx activity to instantiate libGDX Facebook. " + "Looks like you have implemented AndroidApplication without using "
						+ "Activity or Fragment classes or Activity is not available at the moment.");
			}

			Object facebook = ClassReflection.getConstructor(facebookClazz, activityClazz, FacebookConfig.class).newInstance(activity, config);

			setFacebookAPI((FacebookAPI) facebook);

			Gdx.app.debug(TAG, "FacebookAPI for Android loaded successfully.");

		} catch (ReflectionException e) {
			Gdx.app.debug(TAG, "Error creating FacebookAPI for Android (are the libGDX Facebook **.jar files installed?). \n");
			e.printStackTrace();
		}

	}

	private void setFacebookAPI(FacebookAPI facebook) {
		this.facebookAPI = facebook;

	}

	/** @return null if class is not available in runtime */
	private static Class<?> findClass(String name) {
		try {
			return ClassReflection.forName(name);
		} catch (Exception e) {
			return null;
		}
	}

	public FacebookAPI getFacebookAPI() {
		return facebookAPI;
	}

}

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
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class GDXFacebookSystem {

	private GDXFacebookConfig config;

	private Class<?> gdxClazz = null;
	private Object gdxAppObject = null;

	private GDXFacebook gdxFacebook;

	private static GDXFacebookSystem instance;

	private static boolean isInstalled = false;

	private GDXFacebookSystem(GDXFacebookConfig config) {
		this.config = config;
	}

	private void installSystem() {
		installGDXReflections();

		installGDXFacebookForAndroid();
		installGDXFacebookForIOS();
		installGDXFacebookForDesktop();
		// installGDXFacebookForHTML();

		if (gdxFacebook == null) {
			gdxFacebook = new FallbackGDXFacebook();
		}
	}

	public static GDXFacebook install(GDXFacebookConfig config) {
		if (isInstalled) {
			throw new RuntimeException(GDXFacebookSystem.class.getSimpleName() + " has already been installed. You may not call install() more than once.");
		}
		isInstalled = true;
		instance = new GDXFacebookSystem(config);
		instance.installSystem();
		return GDXFacebookSystem.getGDXFacebook();
	}

	private void installGDXReflections() {
		try {
			gdxClazz = ClassReflection.forName("com.badlogic.gdx.Gdx");
			gdxAppObject = ClassReflection.getField(gdxClazz, "app").get(null);

		} catch (ReflectionException e) {
			throw new RuntimeException("No libGDX environment. \n");
		}
	}

	private void installGDXFacebookForIOS() {

		if (Gdx.app.getType() != ApplicationType.iOS) {
			showDebugSkipInstall(ApplicationType.iOS.name());
			return;
		}
		try {

			final Class<?> facebookClazz = ClassReflection.forName("de.tomgrill.gdxfacebook.ios.IOSGDXFacebook");

			Object facebook = ClassReflection.getConstructor(facebookClazz, GDXFacebookConfig.class).newInstance(config);

			gdxFacebook = (GDXFacebook) facebook;

			showDebugInstallSuccessful(ApplicationType.iOS.name());

		} catch (ReflectionException e) {
			showErrorInstall(ApplicationType.iOS.name(), "ios");
			e.printStackTrace();
		}

	}

	private void installGDXFacebookForDesktop() {
		if (Gdx.app.getType() != ApplicationType.Desktop) {
			showDebugSkipInstall(ApplicationType.Desktop.name());
			return;
		}
		try {

			final Class<?> facebookClazz = ClassReflection.forName("de.tomgrill.gdxfacebook.desktop.DesktopGDXFacebook");
			Object facebook = ClassReflection.getConstructor(facebookClazz, GDXFacebookConfig.class).newInstance(config);

			gdxFacebook = (GDXFacebook) facebook;

			showDebugInstallSuccessful(ApplicationType.Desktop.name());

		} catch (ReflectionException e) {
			showErrorInstall(ApplicationType.Desktop.name(), "desktop");
			e.printStackTrace();
		}

	}

	private void installGDXFacebookForHTML() {
		if (Gdx.app.getType() != ApplicationType.WebGL) {
			showDebugSkipInstall(ApplicationType.WebGL.name());
			return;
		}

		try {

			final Class<?> facebookClazz = ClassReflection.forName("de.tomgrill.gdxfacebook.html.HTMLGDXFacebook");
			Object facebook = ClassReflection.getConstructor(facebookClazz, GDXFacebookConfig.class).newInstance(config);

			gdxFacebook = (GDXFacebook) facebook;

			showDebugInstallSuccessful(ApplicationType.WebGL.name());

		} catch (ReflectionException e) {
			showErrorInstall(ApplicationType.WebGL.name(), "html");
			e.printStackTrace();
		}

	}

	private void installGDXFacebookForAndroid() {
		if (Gdx.app.getType() != ApplicationType.Android) {
			showDebugSkipInstall(ApplicationType.Android.name());
			return;
		}

		try {
			Class<?> gdxAndroidEventListenerClazz = ClassReflection.forName("com.badlogic.gdx.backends.android.AndroidEventListener");
			Class<?> activityClazz = ClassReflection.forName("android.app.Activity");
			final Class<?> facebookClazz = ClassReflection.forName("de.tomgrill.gdxfacebook.android.AndroidGDXFacebook");

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

			Object facebook = ClassReflection.getConstructor(facebookClazz, activityClazz, GDXFacebookConfig.class).newInstance(activity, config);

			Method gdxAppAddAndroidEventListenerMethod = ClassReflection.getMethod(gdxAppObject.getClass(), "addAndroidEventListener", gdxAndroidEventListenerClazz);

			gdxAppAddAndroidEventListenerMethod.invoke(gdxAppObject, facebook);

			setGDXFacebook((GDXFacebook) facebook);

			showDebugInstallSuccessful(ApplicationType.Android.name());

		} catch (ReflectionException e) {
			showErrorInstall(ApplicationType.Android.name(), "android");
			e.printStackTrace();
		}

	}

	private static void setGDXFacebook(GDXFacebook facebook) {
		if (instance == null) {
			System.out.println("WTFFFFFFFFFFFFFFFFFFFf99");
		}
		instance.gdxFacebook = facebook;
	}

	private static Class<?> findClass(String name) {
		try {
			return ClassReflection.forName(name);
		} catch (Exception e) {
			return null;
		}
	}

	public static GDXFacebook getGDXFacebook() {
		return instance.gdxFacebook;
	}

	private void showDebugSkipInstall(String os) {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Skip installing " + GDXFacebookVars.LOG_TAG + " for " + os + ". Not running " + os + ". \n");
	}

	private void showErrorInstall(String os, String artifact) {
		Gdx.app.error(GDXFacebookVars.LOG_TAG, "Error installing " + GDXFacebookVars.LOG_TAG + " for " + os + "\n");
		Gdx.app.error(GDXFacebookVars.LOG_TAG, "Did you add compile >> \"de.tomgrill.gdxfacebook:gdx-facebook-" + artifact + ":" + GDXFacebookVars.VERSION
				+ "\" << to your gradle dependencies?\n");
	}

	private void showDebugInstallSuccessful(String os) {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, GDXFacebookVars.LOG_TAG + " for " + os + " installed successfully.");
	}

}

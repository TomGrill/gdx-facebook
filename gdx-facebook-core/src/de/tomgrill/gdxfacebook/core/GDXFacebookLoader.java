package de.tomgrill.gdxfacebook.core;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;


public class GDXFacebookLoader {


    private static GDXFacebookLoader instance;
    private GDXFacebook facebook;
    private GDXFacebookConfig config;

    private GDXFacebookLoader(GDXFacebookConfig config) {
        validateConfig(config);
        this.config = config;
    }


    public static GDXFacebook install(GDXFacebookConfig config) {
        instance = new GDXFacebookLoader(config);
        instance.installSystem();
        return instance.getFacebook();
    }

    private void installSystem() {

        tryToLoadDesktopGDXFacebook();
        tryToLoadHTMLGDXFacebook();
        tryToLoadIOSGDXFacebook();
        tryToLoadAndroidGDXFacebook();

        if (facebook == null) {
            facebook = new FallbackGDXFacebook(config);
        }
    }


    private void tryToLoadAndroidGDXFacebook() {
        if (Gdx.app.getType() != Application.ApplicationType.Android) {
            showDebugSkipInstall(Application.ApplicationType.Android.name());
            return;
        }

        Class<?> gdxClazz;
        Object gdxAppObject;

        try {
            gdxClazz = ClassReflection.forName("com.badlogic.gdx.Gdx");
            gdxAppObject = ClassReflection.getField(gdxClazz, "app").get(null);

        } catch (ReflectionException e) {
            throw new RuntimeException("No libGDX environment. \n");
        }

        try {
            Class<?> gdxAndroidEventListenerClazz = ClassReflection.forName("com.badlogic.gdx.backends.core.AndroidEventListener");
            Class<?> activityClazz = ClassReflection.forName("core.app.Activity");
            final Class<?> facebookClazz = ClassReflection.forName(GDXFacebookVars.CLASSNAME_ANDROID);

            Object activity = null;
            if (ClassReflection.isAssignableFrom(activityClazz, gdxAppObject.getClass())) {
                activity = gdxAppObject;
            } else {
                Class<?> supportFragmentClass = findClass("core.support.v4.app.Fragment");
                if (supportFragmentClass != null && ClassReflection.isAssignableFrom(supportFragmentClass, gdxAppObject.getClass())) {
                    activity = ClassReflection.getMethod(supportFragmentClass, "getActivity").invoke(gdxAppObject);

                } else {

                    Class<?> fragmentClass = findClass("core.app.Fragment");

                    if (fragmentClass != null && ClassReflection.isAssignableFrom(fragmentClass, gdxAppObject.getClass())) {
                        activity = ClassReflection.getMethod(fragmentClass, "getActivity").invoke(gdxAppObject);
                    }
                }
            }

            if (activity == null) {
                throw new RuntimeException("Can't find your gdx activity to instantiate libGDX Facebook. " + "Looks like you have implemented AndroidApplication without using "
                        + "Activity or Fragment classes or Activity is not available at the moment.");
            }

            Object facebookObj = ClassReflection.getConstructor(facebookClazz, activityClazz, GDXFacebookConfig.class).newInstance(activity, config);

            Method gdxAppAddAndroidEventListenerMethod = ClassReflection.getMethod(gdxAppObject.getClass(), "addAndroidEventListener", gdxAndroidEventListenerClazz);

            gdxAppAddAndroidEventListenerMethod.invoke(gdxAppObject, facebookObj);

            facebook = (GDXFacebook) facebookObj;

            showDebugInstallSuccessful(Application.ApplicationType.Android.name());

        } catch (ReflectionException e) {
            showErrorInstall(Application.ApplicationType.Android.name(), "core");
            e.printStackTrace();
        }
    }

    private void tryToLoadIOSGDXFacebook() {
        if (Gdx.app.getType() != Application.ApplicationType.iOS) {
            showDebugSkipInstall(Application.ApplicationType.iOS.name());
            return;
        }
        try {

            final Class<?> facebookClazz = ClassReflection.forName(GDXFacebookVars.CLASSNAME_IOS);

            Object facebookObj = ClassReflection.getConstructor(facebookClazz, GDXFacebookConfig.class).newInstance(config);

            facebook = (GDXFacebook) facebookObj;

            showDebugInstallSuccessful(Application.ApplicationType.iOS.name());

        } catch (ReflectionException e) {
            showErrorInstall(Application.ApplicationType.iOS.name(), "ios");
            e.printStackTrace();
        }
    }

    private void tryToLoadHTMLGDXFacebook() {
        if (Gdx.app.getType() != Application.ApplicationType.WebGL) {
            showDebugSkipInstall(Application.ApplicationType.WebGL.name());
            return;
        }

        try {

            final Class<?> facebookClazz = ClassReflection.forName(GDXFacebookVars.CLASSNAME_HTML);
            Object facebookObj = ClassReflection.getConstructor(facebookClazz, GDXFacebookConfig.class).newInstance(config);

            facebook = (GDXFacebook) facebookObj;

            showDebugInstallSuccessful(Application.ApplicationType.WebGL.name());

        } catch (ReflectionException e) {
            showErrorInstall(Application.ApplicationType.WebGL.name(), "html");
            e.printStackTrace();
        }
    }

    private void tryToLoadDesktopGDXFacebook() {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            showDebugSkipInstall(Application.ApplicationType.Desktop.name());
            return;
        }


        try {

            final Class<?> facebookClazz = ClassReflection.forName(GDXFacebookVars.CLASSNAME_DESKTOP);
            Object facebookObj = ClassReflection.getConstructor(facebookClazz, GDXFacebookConfig.class).newInstance(config);

            facebook = (GDXFacebook) facebookObj;

            showDebugInstallSuccessful(Application.ApplicationType.Desktop.name());

        } catch (ReflectionException e) {
            showErrorInstall(Application.ApplicationType.Desktop.name(), "desktop");
        }
    }

    private void validateConfig(GDXFacebookConfig config) {
        if (config == null) {
            throw new NullPointerException(GDXFacebookConfig.class.getSimpleName() + "may not be null.");
        }

        if (config.PREF_FILENAME == null) {
            throw new NullPointerException("GDXFacebookConfig.class.getSimpleName() + \": PREF_FILENAME may bot be null.");
        }

        if (config.PREF_FILENAME.length() == 0) {
            throw new RuntimeException(GDXFacebookConfig.class.getSimpleName() + ": PREF_FILENAME is empty.");
        }

        if (config.APP_ID == null) {
            throw new NullPointerException("GDXFacebookConfig.class.getSimpleName() + \": APP_ID may bot be null.");
        }

        Long.valueOf(config.APP_ID);
    }

    public GDXFacebook getFacebook() {
        return facebook;
    }

    private void showDebugSkipInstall(String os) {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Skip installing " + GDXFacebookVars.LOG_TAG + " for " + os + ". Not running " + os + ". \n");
    }

    private void showErrorInstall(String os, String artifact) {
        Gdx.app.error(GDXFacebookVars.LOG_TAG, "Error installing " + GDXFacebookVars.LOG_TAG + " for " + os + "\n");
        Gdx.app.error(GDXFacebookVars.LOG_TAG, "Did you add >> compile \"de.tomgrill.gdxfacebook:gdx-facebook-" + artifact + ":" + GDXFacebookVars.VERSION
                + "\" << to your gradle dependencies? View https://github.com/TomGrill/gdx-facebook/wiki for more information.\n");
    }

    private void showDebugInstallSuccessful(String os) {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, GDXFacebookVars.LOG_TAG + " for " + os + " installed successfully.");
    }

    private static Class<?> findClass(String name) {
        try {
            return ClassReflection.forName(name);
        } catch (Exception e) {
            return null;
        }
    }
}

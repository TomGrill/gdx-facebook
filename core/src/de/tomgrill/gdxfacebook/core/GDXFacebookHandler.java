package de.tomgrill.gdxfacebook.core;

public class GDXFacebookHandler {

    private static GDXFacebook gdxFacebook;


    public static void setup(GDXFacebook facebook) {
//        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
//            try {
//                ClassReflection.forName("com.badlogic.gdx.Gdx");
//            } catch (ReflectionException e) {
//                e.printStackTrace();
//            }
//        }

        GDXFacebookHandler.gdxFacebook = facebook;
    }

    public static GDXFacebook FB() {
        return GDXFacebookHandler.gdxFacebook;
    }
}

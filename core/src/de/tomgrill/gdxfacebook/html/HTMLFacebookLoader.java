package de.tomgrill.gdxfacebook.html;

import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.FacebookLoader;

public class HTMLFacebookLoader implements FacebookLoader {
    @Override
    public GDXFacebook load(GDXFacebookConfig config) {

        JSNIFacebookSDK.initSDK(config.APP_ID, config.GRAPH_API_VERSION);

        return new HTMLGDXFacebook(config);
    }
}

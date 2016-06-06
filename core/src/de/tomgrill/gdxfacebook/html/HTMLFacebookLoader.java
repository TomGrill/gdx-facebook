package de.tomgrill.gdxfacebook.html;

import de.tomgrill.gdxfacebook.core.FacebookLoader;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;

public class HTMLFacebookLoader implements FacebookLoader {

    private HTMLGDXFacebook htmlgdxFacebook;

    @Override
    public GDXFacebook load(final GDXFacebookConfig config) {

        htmlgdxFacebook = new HTMLGDXFacebook(config);

        JSNIFacebookSDK.initSDK(config.APP_ID, config.GRAPH_API_VERSION, new InitCallback() {
            @Override
            public void loaded() {
                htmlgdxFacebook.loaded = true;
            }
        });

        return htmlgdxFacebook;

    }
}

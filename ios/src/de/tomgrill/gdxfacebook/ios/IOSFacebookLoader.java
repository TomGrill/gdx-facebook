package de.tomgrill.gdxfacebook.ios;

import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.FacebookLoader;

public class IOSFacebookLoader implements FacebookLoader {
    @Override
    public GDXFacebook load(GDXFacebookConfig config) {
        return new IOSGDXFacebook(config);
    }
}

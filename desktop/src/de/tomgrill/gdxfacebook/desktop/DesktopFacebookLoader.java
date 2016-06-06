package de.tomgrill.gdxfacebook.desktop;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.FacebookLoader;

public class DesktopFacebookLoader implements FacebookLoader {
    @Override
    public GDXFacebook load(GDXFacebookConfig config) {
        return new DesktopGDXFacebook(config);
    }
}

package de.tomgrill.gdxfacebook.html;

import com.badlogic.gdx.utils.JsonValue;

public interface LoginCallback {

    void success(String userId, String accessToken, String permissions);

    void fail();
}

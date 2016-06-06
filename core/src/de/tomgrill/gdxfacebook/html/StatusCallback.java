package de.tomgrill.gdxfacebook.html;

public interface StatusCallback {
    void connected(String accessToken, String expiresIn);
    void notAuthorized();
    void disconnected();

}

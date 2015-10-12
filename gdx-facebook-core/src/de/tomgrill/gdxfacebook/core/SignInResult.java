package de.tomgrill.gdxfacebook.core;

public class SignInResult extends Result {

    private AccessToken accessToken;

    public SignInResult(AccessToken accessToken, String message) {
        super(message);
        setAccessToken(accessToken);
    }


    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}

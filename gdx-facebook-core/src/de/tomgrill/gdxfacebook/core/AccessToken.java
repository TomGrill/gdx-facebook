package de.tomgrill.gdxfacebook.core;

public class AccessToken {

    private String token;
    private int expiresIn;


    public AccessToken(String token, int expiresIn) {
        setToken(token);
        setExpiresIn(expiresIn);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}

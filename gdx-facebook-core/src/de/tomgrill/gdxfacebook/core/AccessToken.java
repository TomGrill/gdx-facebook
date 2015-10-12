package de.tomgrill.gdxfacebook.core;

public class AccessToken {

    private String token;
    private long expiresAt;


    public AccessToken(String token, long expiresIn) {
        setToken(token);
        setExpiresAt(expiresIn);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (token == null) {
            throw new NullPointerException("token may not be null.");
        }
        this.token = token;
    }


    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }
}

package de.tomgrill.gdxfacebook.core;

public class Result {

    private String message;

    public Result(String message) {
        setMessage(message);
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

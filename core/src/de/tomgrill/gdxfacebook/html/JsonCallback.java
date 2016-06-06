package de.tomgrill.gdxfacebook.html;

public interface JsonCallback {
    void jsonResult(String json);

    void error();
}

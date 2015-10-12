package de.tomgrill.gdxfacebook.core;

public class GraphError {

    private String errorMesssage;

    public GraphError(String errorMesssage) {
        setErrorMesssage(errorMesssage);
    }


    public String getErrorMesssage() {
        return errorMesssage;
    }

    public void setErrorMesssage(String errorMesssage) {
        this.errorMesssage = errorMesssage;
    }
}

package de.tomgrill.gdxfacebook.core;

public class GDXFacebookCallbackAdapter<T extends Result> implements GDXFacebookCallback<T> {
    @Override
    public void onSuccess(T result) {

    }

    @Override
    public void onError(GraphError error) {

    }

    @Override
    public void onFail(Throwable t) {

    }

    @Override
    public void onCancel() {

    }
}

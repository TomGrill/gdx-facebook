package de.tomgrill.gdxfacebook.html;

class JSNIJavascript {
    static native void consoleLog(String msg)
        /*-{
            console.log(msg);
        }-*/
    ;
}

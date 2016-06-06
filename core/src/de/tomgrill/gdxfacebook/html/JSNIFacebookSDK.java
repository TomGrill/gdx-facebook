package de.tomgrill.gdxfacebook.html;

class JSNIFacebookSDK {


    static native void FBapi(String graph, String method, String fields, JsonCallback callback)
    /*-{
//        console.log(fields);
        $wnd.FB.api('/' + graph, method, eval('({' + fields + '})'), function(response) {


            if (!response || response.error) {
                console.log("Error occurred: ");
                console.log(response);
                callback.@de.tomgrill.gdxfacebook.html.JsonCallback::error()();
            } else {
                console.log(response);
                callback.@de.tomgrill.gdxfacebook.html.JsonCallback::jsonResult(Ljava/lang/String;)(JSON.stringify(response));
            }


        });
    }-*/;




    static native void FBLoginState(StatusCallback callback)
    /*-{

        $wnd.FB.getLoginStatus(function(response) {
            console.log(response);
            if (response.status === 'connected') {
                callback.@de.tomgrill.gdxfacebook.html.StatusCallback::connected(Ljava/lang/String; Ljava/lang/String;)(response.authResponse.accessToken, response.authResponse.expiresIn);
            } else if (response.status === 'not_authorized') {
                callback.@de.tomgrill.gdxfacebook.html.StatusCallback::notAuthorized()();
            } else {
                // The person is not logged into Facebook, so we're not sure if they are logged into this app or not.
                callback.@de.tomgrill.gdxfacebook.html.StatusCallback::disconnected()();
            }
        });
    }-*/;

    static native void FBLogin(String permissions, LoginCallback callback)
    /*-{

        $wnd.FB.login(function(response) {
//            console.log(response);
            if (response.authResponse) {
                callback.@de.tomgrill.gdxfacebook.html.LoginCallback::success(Ljava/lang/String; Ljava/lang/String; Ljava/lang/String;)(response.authResponse.accessToken, response.authResponse.expiresIn, response.authResponse.grantedScopes);
            } else {
                callback.@de.tomgrill.gdxfacebook.html.LoginCallback::fail()();
            }

//            console.log("login finished with " + permissions);

        }, {scope: permissions, return_scopes: true});

	}-*/;


    /**
     * Nearly the same setup as recommend by the quick start guide:
     * https://developers.facebook.com/docs/javascript/quickstart
     * <p>
     * Notice: Usage of $wnd and $doc
     */
    static native void initSDK(String appIdNr, String apiVersion, InitCallback callback)
    /*-{
        $wnd.fbAsyncInit = function() {
            $wnd.FB.init({
                appId      : appIdNr,
                cookie     : true,
                xfbml      : false,
                version    : apiVersion,
            });
            callback.@de.tomgrill.gdxfacebook.html.InitCallback::loaded()();
        };

        // Load the SDK asynchronously
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk/debug.js";
            fjs.parentNode.insertBefore(js, fjs);
        }($doc, 'script', 'facebook-jssdk'));

        return true;

	}-*/;
}

package de.tomgrill.gdxfacebook.html;

class JSNIFacebookSDK {
    static native void FBLogin() /*-{

	  $wnd.FB.login(function(response) {
                        // handle the response
                      }, {scope: 'public_profile,email'});




	}-*/;


    /**
     * Nearly the same setup as recommend by the quick start guide:
     * https://developers.facebook.com/docs/javascript/quickstart
     *
     * Notice: Usage of $wnd and $doc
     */
    static native void initSDK(String appIdNr, String apiVersion)
    /*-{
        $wnd.fbAsyncInit = function() {
            $wnd.FB.init({
                appId      : appIdNr,
                cookie     : true,
                xfbml      : false,
                version    : apiVersion,
            });

        };

        // Load the SDK asynchronously
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk/debug.js";
            fjs.parentNode.insertBefore(js, fjs);
        }($doc, 'script', 'facebook-jssdk'));



	}-*/;
}

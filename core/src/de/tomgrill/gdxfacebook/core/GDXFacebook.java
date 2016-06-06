package de.tomgrill.gdxfacebook.core;

import com.badlogic.gdx.utils.Array;

public interface GDXFacebook {

    /**
     * Opens the sign in dialog of the underlying Facebook SDK in the following cases:
     * - User has not authorized app
     * - User has authorized app but needs to grant permissions
     * - An exisitng access_token is no longer valid.
     * <p>
     * Silently signs the user in when:
     * - A existing token is loaded and still valid.
     * - The underlying Facebook SDK can handle the sign in silently.
     *
     * @param mode signIn mode
     * @param permissions required permissions
     * @param callback callback to handle result
     */
    void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback);


    /**
     * Make a Game Request which uses the underlaying Facebook SDK. More at: https://developers.facebook.com/docs/games/requests/
     *
     * @param request the request
     * @param callback callback to handle result
     *
     * @deprecated since v1.2.0 will be removed with v1.4.0. Use {@link #gameRequest(GDXFacebookGameRequest, GDXFacebookCallback)} instead.
     */
    @Deprecated
    void showGameRequest(GDXFacebookGameRequest request, GDXFacebookCallback<GameRequestResult> callback);


    /**
     * Make a Game Request which uses the underlaying Facebook SDK. More at: https://developers.facebook.com/docs/games/requests/
     *
     * @param request the request
     * @param callback callback to handle result
     */
    void gameRequest(GDXFacebookGameRequest request, GDXFacebookCallback<GameRequestResult> callback);


    /**
     * Make a new Graph API Request. View: https://developers.facebook.com/docs/graph-api/reference/request
     *
     * @param request the request
     * @param callback the callback
     *
     * @deprecated since v1.2.0 will be removed with v1.4.0
     */
    @Deprecated
    void newGraphRequest(Request request, final GDXFacebookCallback<JsonResult> callback);

    /**
     * Make a new Graph API Request. View: https://developers.facebook.com/docs/graph-api/reference/request
     *
     * @param request  the request
     * @param callback the callback
     */
    void graph(Request request, final GDXFacebookCallback<JsonResult> callback);

    /**
     * Signs the user out. When keepSessionData is false then all session data for the current user
     * will be deleted and the user needs to login and auth the app again. When keepSessionData is true
     * the session data is stored and reused with the next login.
     * NOTE: When the user has native Facebook App installed on his device keepSessionData = false does not always work.
     * In this cases you may want to give the user a hint that he needs to logout from Facebook App in order to be able to switch accounts.
     *
     * @param keepSessionData whether to keep the user session data for later or not.
     */
    void signOut(boolean keepSessionData);

    /**
     * Convenient method for {@link #signOut(boolean)}.
     *
     */

    void signOut();

    boolean isSignedIn();

    GDXFacebookAccessToken getAccessToken();

    boolean isLoaded();
}

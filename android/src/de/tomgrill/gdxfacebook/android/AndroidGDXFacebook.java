/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.tomgrill.gdxfacebook.android;

import android.app.Activity;
import android.content.Intent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidEventListener;
import com.badlogic.gdx.utils.Array;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;
import de.tomgrill.gdxfacebook.core.*;

import java.util.ArrayList;
import java.util.Collection;


public class AndroidGDXFacebook extends GDXFacebookBasic implements AndroidEventListener {

    private Activity activity;
    private CallbackManager callbackManager;
    private String userId;
    private SignInMode signInMode;


    public AndroidGDXFacebook(final Activity activity, final GDXFacebookConfig config) {
        super(config);
        this.activity = activity;

        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        AppEventsLogger.activateApp(activity.getApplication());
        callbackManager = CallbackManager.Factory.create();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void startGUISignIn() {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting GUI sign in.");
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = new GDXFacebookAccessToken(AccessToken.getCurrentAccessToken().getToken(), AccessToken.getCurrentAccessToken().getExpires().getTime());
                storeNewToken(accessToken);
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign successful. User token: " + accessToken.getToken());
                callback.onSuccess(new SignInResult(accessToken, "Sign in successful."));
            }

            @Override
            public void onCancel() {
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign fail by user.");
                callback.onCancel();
            }

            @Override
            public void onError(FacebookException e) {
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Error while trying to sign in: " + e.getMessage());
                callback.onError(new GDXFacebookError(e.getMessage()));
            }
        });

        if (this.signInMode == SignInMode.PUBLISH) {
            LoginManager.getInstance().logInWithPublishPermissions(activity, gdxArrayToCollection(permissions));
        } else {
            LoginManager.getInstance().logInWithReadPermissions(activity, gdxArrayToCollection(permissions));
        }
    }

    private Collection<String> gdxArrayToCollection(Array<String> array) {
        Collection<String> col = new ArrayList<String>();
        for (int i = 0; i < array.size; i++) {
            col.add(array.get(i));
        }
        return col;
    }

    @Override
    public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback) {
        this.callback = callback;
        this.permissions = permissions;
        this.signInMode = mode;

        /**
         * Check whether we can reuse an existing token:
         *
         * If the user has Facebook App installed
         * AccessToken.getCurrentAccessToken() will not be NULL
         *
         * Only when the Facebook App is not installed we need to setup the token
         * manually.
         * */
        loadAccessToken();

        if (accessToken == null && AccessToken.getCurrentAccessToken() != null) {
            accessToken = new GDXFacebookAccessToken(AccessToken.getCurrentAccessToken().getToken(), AccessToken.getCurrentAccessToken().getExpires().getTime());
        }


        if (accessToken != null) {
            startSilentSignIn();
        } else {
            startGUISignIn();
        }

    }

    @Override
    @Deprecated
    public void showGameRequest(final GDXFacebookGameRequest request, final GDXFacebookCallback<GameRequestResult> gameRequestCallback) {
        gameRequest(request, gameRequestCallback);
    }

    @Override
    public void gameRequest(final GDXFacebookGameRequest request, final GDXFacebookCallback<GameRequestResult> gameRequestCallback) {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting Game Request dialog.");

        GameRequestContent.Builder builder = new GameRequestContent.Builder();

        if (request.getMessage() != null) builder.setMessage(request.getMessage());
        if (request.getData() != null) builder.setData(request.getData());
        if (request.getTitle() != null) builder.setTitle(request.getTitle());
        if (request.getObjectId() != null) builder.setObjectId(request.getObjectId());

        Array<String> suggestions = request.getSuggestions();
        if (suggestions != null && suggestions.size > 0) {
            ArrayList<String> suggestionList = new ArrayList<String>();
            for (int i = 0; i < suggestions.size; i++) {
                suggestionList.add(suggestions.get(i));
            }
            builder.setSuggestions(suggestionList);
        }

        Array<String> recipients = request.getRecipients();
        if (recipients != null && recipients.size > 0) {
            ArrayList<String> recipientsList = new ArrayList<String>();
            for (int i = 0; i < recipients.size; i++) {
                recipientsList.add(recipients.get(i));
            }
            builder.setRecipients(recipientsList);
        }


        if (request.getActionType() != null) {
            switch (request.getActionType()) {
                case ASKFOR:
                    builder.setActionType(GameRequestContent.ActionType.ASKFOR);
                    break;
                case SEND:
                    builder.setActionType(GameRequestContent.ActionType.SEND);
                    break;
                case TURN:
                    builder.setActionType(GameRequestContent.ActionType.TURN);
                    break;
            }
        }

        if (request.getFilters() != null) {
            switch (request.getFilters()) {
                case APP_NON_USERS:
                    builder.setFilters(GameRequestContent.Filters.APP_NON_USERS);
                    break;
                case APP_USERS:
                    builder.setFilters(GameRequestContent.Filters.APP_USERS);
                    break;
            }
        }


        GameRequestContent content = builder.build();


        GameRequestDialog requestDialog = new GameRequestDialog(activity);

        requestDialog.registerCallback(callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
            public void onSuccess(GameRequestDialog.Result result) {

                Array<String> recipients = new Array<String>();
                for (int i = 0; i < result.getRequestRecipients().size(); i++) {
                    recipients.add(result.getRequestRecipients().get(i));
                }

                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "User finished Game Request dialog successful.");
                gameRequestCallback.onSuccess(new GameRequestResult(result.getRequestId(), recipients));
            }

            public void onCancel() {
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Game Request has been fail.");
                gameRequestCallback.onCancel();
            }

            public void onError(FacebookException error) {
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Game Request finished with error: " + error.getMessage());
                gameRequestCallback.onError(new GDXFacebookError(error.getMessage()));
            }
        });
        requestDialog.show(content);
    }

    @Override
    protected void loadAccessToken() {
        super.loadAccessToken();
        loadUserId();
    }

    @Override
    protected void storeNewToken(GDXFacebookAccessToken token) {
        super.storeNewToken(token);
        storeUserId();
    }

    private void storeUserId() {
        preferences.putString("user_id", userId);
        preferences.flush();
    }

    private void loadUserId() {
        userId = preferences.getString("user_id", null);
    }

    @Override
    public void signOut(boolean keepSessionData) {
        super.signOut(keepSessionData);
        userId = null;
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        LoginManager.getInstance().logOut();


        if (keepSessionData == SignOutMode.DELETE_SESSION_DATA) {
            AccessToken.setCurrentAccessToken(null);

            deleteTokenData();
        }
    }

    @Override
    public boolean isLoaded() {
        return true;
    }
}

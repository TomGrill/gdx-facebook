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


package de.tomgrill.gdxfacebook.iosmoe;

import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import apple.foundation.NSMutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.utils.Array;

import apple.uikit.UIViewController;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.core.fbsdkcorekit.FBSDKAccessToken;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.login.fbsdkloginkit.FBSDKLoginManager;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.login.fbsdkloginkit.FBSDKLoginManagerLoginResult;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.FBSDKGameRequestContent;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.FBSDKGameRequestDialog;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.enums.FBSDKGameRequestActionType;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.enums.FBSDKGameRequestFilter;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.protocol.FBSDKGameRequestDialogDelegate;
import de.tomgrill.gdxfacebook.core.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class IOSMOEGDXFacebook extends GDXFacebookBasic {

    private FBSDKLoginManager loginManager;
    private SignInMode signInMode;
    private UIViewController uiViewController;

    public IOSMOEGDXFacebook(GDXFacebookConfig config) {
        super(config);

        loginManager = FBSDKLoginManager.alloc();

        uiViewController = ((IOSApplication)Gdx.app).getUIViewController();
    }

    @Override
    public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback) {
        this.callback = callback;
        this.permissions = permissions;
        this.signInMode = mode;

        loadAccessToken();

        if (accessToken == null && FBSDKAccessToken.currentAccessToken() != null) {
            accessToken = new GDXFacebookAccessToken(FBSDKAccessToken.currentAccessToken().tokenString(), (long) FBSDKAccessToken.currentAccessToken().expirationDate().timeIntervalSince1970());
        }


        if (accessToken != null) {
            startSilentSignIn();
        } else {
            startGUISignIn();
        }
    }

    @Override
    public void showGameRequest(GDXFacebookGameRequest request, final GDXFacebookCallback<GameRequestResult> gameRequestCallback) {
        gameRequest(request, gameRequestCallback);
    }

    @Override
    public void gameRequest(final GDXFacebookGameRequest request, final GDXFacebookCallback<GameRequestResult> gameRequestCallback) {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting Game Request dialog.");

        FBSDKGameRequestContent gameRequestContent = FBSDKGameRequestContent.alloc();


        if (request.getMessage() != null) gameRequestContent.setMessage(request.getMessage());
        if (request.getData() != null) gameRequestContent.setData(request.getData());
        if (request.getTitle() != null) gameRequestContent.setTitle(request.getTitle());
        if (request.getObjectId() != null) gameRequestContent.setObjectID(request.getObjectId());

        Array<String> suggestions = request.getSuggestions();
        if (suggestions != null && suggestions.size > 0) {
            NSMutableArray suggestionList = NSMutableArray.array();

            for (int i = 0; i < suggestions.size; i++) {
                suggestionList.addObject(suggestions.get(i));
            }
            gameRequestContent.setRecipientSuggestions(suggestionList);
        }

        Array<String> recipients = request.getRecipients();
        if (recipients != null && recipients.size > 0) {
            NSMutableArray recipientsList = NSMutableArray.array ();

            for (int i = 0; i < recipients.size; i++) {
                recipientsList.addObject(recipients.get(i));
            }
            gameRequestContent.setRecipients(recipientsList);
        }


        if (request.getActionType() != null) {
            switch (request.getActionType()) {
                case ASKFOR:
                    gameRequestContent.setActionType(FBSDKGameRequestActionType.AskFor);
                    break;
                case SEND:
                    gameRequestContent.setActionType(FBSDKGameRequestActionType.Send);
                    break;
                case TURN:
                    gameRequestContent.setActionType(FBSDKGameRequestActionType.Turn);
                    break;
            }
        }

        if (request.getFilters() != null) {
            switch (request.getFilters()) {
                case APP_NON_USERS:
                    gameRequestContent.setFilters(FBSDKGameRequestFilter.AppNonUsers);
                    break;
                case APP_USERS:
                    gameRequestContent.setFilters(FBSDKGameRequestFilter.AppUsers);
                    break;
            }
        }

        FBSDKGameRequestDialog.showWithContentDelegate(gameRequestContent, new FBSDKGameRequestDialogDelegate() {
            @Override
            public void gameRequestDialogDidCompleteWithResults(FBSDKGameRequestDialog gameRequestDialog, NSDictionary<?, ?> results) {
                Array<String> recipients = new Array<String>();

                String requestId = "";

                for (Map.Entry<?, ?> entry : results.entrySet()) {
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();

                    if (key.equals("request")) {
                        requestId = value;
                    } else {
                        recipients.add(value);
                    }
                }

                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "User finished Game Request dialog successful.");
                gameRequestCallback.onSuccess(new GameRequestResult(requestId, recipients));
            }

            @Override
            public void gameRequestDialogDidFailWithError(FBSDKGameRequestDialog gameRequestDialog, NSError error) {
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Game Request finished with error: " + error.localizedDescription());
                gameRequestCallback.onError(new GDXFacebookError(error.localizedDescription()));
            }

            @Override
            public void gameRequestDialogDidCancel(FBSDKGameRequestDialog gameRequestDialog) {
                Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Game Request has been fail.");
                gameRequestCallback.onCancel();
            }
        });
    }

    @Override
    public void signOut(boolean keepSessionData) {
        super.signOut(keepSessionData);
        loginManager.logOut();

        if (keepSessionData == SignOutMode.DELETE_SESSION_DATA) {
            FBSDKAccessToken.setCurrentAccessToken(null);

            deleteTokenData();
        }
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    protected void startGUISignIn() {
        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting GUI sign in.");

        NSMutableArray listPermissions = NSMutableArray.array ();

        for (int i = 0; i < permissions.size; i++) {
            listPermissions.addObject(permissions.get(i));
        }

        if (this.signInMode == SignInMode.PUBLISH) {
            FBSDKLoginManager.Block_logInWithPublishPermissionsFromViewControllerHandler result = new FBSDKLoginManager.Block_logInWithPublishPermissionsFromViewControllerHandler() {
                @Override
                public void call_logInWithPublishPermissionsFromViewControllerHandler(FBSDKLoginManagerLoginResult loginResult, NSError nsError) {
                    if (nsError != null) {
                        IOSMOEGDXFacebook.this.signOut();
                        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Error while trying to sign in: " + nsError.localizedDescription());
                        callback.onError(new GDXFacebookError(nsError.localizedDescription()));
                    } else if (loginResult.isCancelled()) {
                        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign fail by user.");
                        IOSMOEGDXFacebook.this.signOut();
                        callback.onCancel();
                    } else {
                        accessToken = new GDXFacebookAccessToken(FBSDKAccessToken.currentAccessToken().tokenString(), (long) FBSDKAccessToken.currentAccessToken().expirationDate().timeIntervalSince1970());
                        IOSMOEGDXFacebook.this.storeNewToken(accessToken);
                        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign successful. User token: " + accessToken.getToken());
                        callback.onSuccess(new SignInResult(accessToken, "Sign in successful."));
                    }
                }
            };

            loginManager.logInWithPublishPermissionsFromViewControllerHandler(listPermissions, uiViewController, result);
        } else {
            FBSDKLoginManager.Block_logInWithReadPermissionsFromViewControllerHandler result = new FBSDKLoginManager.Block_logInWithReadPermissionsFromViewControllerHandler() {
                @Override
                public void call_logInWithReadPermissionsFromViewControllerHandler(FBSDKLoginManagerLoginResult loginResult, NSError nsError) {
                    if (nsError != null) {
                        IOSMOEGDXFacebook.this.signOut();
                        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Error while trying to sign in: " + nsError.localizedDescription());
                        callback.onError(new GDXFacebookError(nsError.localizedDescription()));
                    } else if (loginResult.isCancelled()) {
                        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign fail by user.");
                        IOSMOEGDXFacebook.this.signOut();
                        callback.onCancel();
                    } else {
                        accessToken = new GDXFacebookAccessToken(FBSDKAccessToken.currentAccessToken().tokenString(), (long) FBSDKAccessToken.currentAccessToken().expirationDate().timeIntervalSince1970());
                        IOSMOEGDXFacebook.this.storeNewToken(accessToken);
                        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign successful. User token: " + accessToken.getToken());
                        callback.onSuccess(new SignInResult(accessToken, "Sign in successful."));
                    }
                }
            };

            loginManager.logInWithReadPermissionsFromViewControllerHandler(listPermissions, uiViewController, result);
        }
    }

}

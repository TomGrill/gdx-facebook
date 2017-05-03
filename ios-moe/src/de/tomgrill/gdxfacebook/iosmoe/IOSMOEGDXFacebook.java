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
import apple.foundation.NSMutableDictionary;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.JsonWriter;
import de.tomgrill.gdxfacebook.core.*;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.core.fbsdkcorekit.FBSDKAccessToken;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.core.fbsdkcorekit.FBSDKGraphRequest;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.core.fbsdkcorekit.FBSDKGraphRequestConnection;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.login.fbsdkloginkit.FBSDKLoginManager;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.login.fbsdkloginkit.FBSDKLoginManagerLoginResult;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.FBSDKGameRequestContent;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.FBSDKGameRequestDialog;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.enums.FBSDKGameRequestActionType;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.enums.FBSDKGameRequestFilter;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.protocol.FBSDKGameRequestDialogDelegate;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.objc.map.ObjCObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class IOSMOEGDXFacebook extends GDXFacebookBasic {

    private FBSDKLoginManager loginManager;
    private SignInMode signInMode;

    public IOSMOEGDXFacebook(GDXFacebookConfig config) {
        super(config);

        loginManager = FBSDKLoginManager.alloc().init();
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
            NSMutableArray recipientsList = NSMutableArray.array();

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

        NSMutableArray listPermissions = NSMutableArray.array();

        for (int i = 0; i < permissions.size; i++) {
            listPermissions.addObject(permissions.get(i));
        }

        if (this.signInMode == SignInMode.PUBLISH) {
            loginManager.logInWithPublishPermissionsFromViewControllerHandler(listPermissions, ((IOSApplication) Gdx.app).getUIViewController(), new FBSDKLoginManager.Block_logInWithPublishPermissionsFromViewControllerHandler() {
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
                        Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign successful. User token: " + accessToken.getToken());
                        callback.onSuccess(new SignInResult(accessToken, "Sign in successful."));
                    }
                }
            });
        } else {
            loginManager.logInWithReadPermissionsFromViewControllerHandler(listPermissions, ((IOSApplication) Gdx.app).getUIViewController(), new FBSDKLoginManager.Block_logInWithReadPermissionsFromViewControllerHandler() {
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
            });
        }
    }

    @Override
    protected void startSilentSignIn() {
        if (accessToken != null) {
            for(int i = 0, size = permissions.size; i < size; i++) {
                if(!FBSDKAccessToken.currentAccessToken().hasGranted(permissions.get(i))) {
                    signOut();
                    Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Used access_token is valid but new permissions need to be granted. Need GUI sign in.");
                    callback.onError(new GDXFacebookError("Used access_token is valid but new permissions need to be granted. Need GUI sign in."));
                    startGUISignIn();
                    return;
                }
            }
        }
    }

    @Override
    public void graph(Request request, GDXFacebookCallback<JsonResult> callback) {
        if (!(request instanceof AbstractRequest)) {
            callback.onError(new GDXFacebookError("Request is not an AbstractRequest!"));
            return;
        }

        NSMutableDictionary converted = NSMutableDictionary.dictionary();
        ArrayMap<String, String> fields = ((AbstractRequest) request).getFields();
        for (int i = 0, size = fields.size; i < size; i++) {
            converted.put(fields.getKeyAt(i), fields.getValueAt(i));
        }

        FBSDKGraphRequest graphRequest = FBSDKGraphRequest.alloc().initWithGraphPathParameters(request.getNode(), converted);
        graphRequest.startWithCompletionHandler(new FBSDKGraphRequest.Block_startWithCompletionHandler() {
            @Override
            public void call_startWithCompletionHandler(FBSDKGraphRequestConnection connection, @Mapped(ObjCObjectMapper.class) Object result, NSError error) {
                if (error == null) {
                    Gdx.app.debug("Graph request ", "success!");
                    JsonWriter writer = new JsonWriter(new StringWriter());
                    NSDictionary dict = (NSDictionary) result;
                    try {
                        writer.object();
                        for (int i = 0; i < dict.size(); i++) {
                            Object key = dict.allKeys().get(i);
                            Object value = dict.allValues().get(i);

                            writer.name(String.valueOf(key)).value(String.valueOf(value));
                        }
                        writer.close();

                        callback.onSuccess(new JsonResult(writer.getWriter().toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(new GDXFacebookError(e.getMessage()));
                    }
                } else {
                    Gdx.app.debug("Error graph", error.localizedDescription());
                    callback.onError(new GDXFacebookError(error.localizedDescription()));
                }
            }
        });
    }
}

/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.tomgrill.gdxfacebook.ios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.facebook.core.FBSDKAccessToken;
import org.robovm.pods.facebook.login.FBSDKLoginBehavior;
import org.robovm.pods.facebook.login.FBSDKLoginManager;
import org.robovm.pods.facebook.login.FBSDKLoginManagerLoginResult;
import org.robovm.pods.facebook.share.FBSDKGameRequestActionType;
import org.robovm.pods.facebook.share.FBSDKGameRequestContent;
import org.robovm.pods.facebook.share.FBSDKGameRequestDialog;
import org.robovm.pods.facebook.share.FBSDKGameRequestDialogDelegateAdapter;
import org.robovm.pods.facebook.share.FBSDKGameRequestFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookAccessToken;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.core.GDXFacebookGameRequest;
import de.tomgrill.gdxfacebook.core.GDXFacebookVars;
import de.tomgrill.gdxfacebook.core.GameRequestResult;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.core.SignInResult;

public class IOSGDXFacebook extends GDXFacebook {

	private FBSDKLoginManager loginManager;
	private SignInMode signInMode;

	public IOSGDXFacebook(GDXFacebookConfig config) {
		super(config);

		loginManager = new FBSDKLoginManager();
		loginManager.setLoginBehavior(FBSDKLoginBehavior.Native);
	}

	@Override
	public void signIn(SignInMode mode, Array<String> permissions, GDXFacebookCallback<SignInResult> callback) {
		this.callback = callback;
		this.permissions = permissions;
		this.signInMode = mode;

		loadAccessToken();

		if (accessToken == null && FBSDKAccessToken.getCurrentAccessToken() != null) {
			accessToken = new GDXFacebookAccessToken(FBSDKAccessToken.getCurrentAccessToken().getTokenString(), FBSDKAccessToken.getCurrentAccessToken().getExpirationDate().toDate().getTime());
		}


		if (accessToken != null) {
			startSilentSignIn();
		} else {
			startGUISignIn();
		}
	}

	@Override
	public void showGameRequest(GDXFacebookGameRequest request, final GDXFacebookCallback<GameRequestResult> gameRequestCallback) {

		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting Game Request dialog.");

		FBSDKGameRequestContent gameRequestContent = new FBSDKGameRequestContent();


		if (request.getMessage() != null) gameRequestContent.setMessage(request.getMessage());
		if (request.getData() != null) gameRequestContent.setData(request.getData());
		if (request.getTitle() != null) gameRequestContent.setTitle(request.getTitle());
		if (request.getObjectId() != null) gameRequestContent.setObjectID(request.getObjectId());

		Array<String> suggestions = request.getSuggestions();
		if (suggestions != null && suggestions.size > 0) {
			ArrayList<String> suggestionList = new ArrayList<String>();
			for (int i = 0; i < suggestions.size; i++) {
				suggestionList.add(suggestions.get(i));
			}
			gameRequestContent.setRecipientSuggestions(suggestionList);
		}

		Array<String> recipients = request.getRecipients();
		if (recipients != null && recipients.size > 0) {
			ArrayList<String> recipientsList = new ArrayList<String>();
			for (int i = 0; i < recipients.size; i++) {
				recipientsList.add(recipients.get(i));
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

		FBSDKGameRequestDialog.show(gameRequestContent, new FBSDKGameRequestDialogDelegateAdapter() {
			@Override
			public void didComplete(FBSDKGameRequestDialog gameRequestDialog, NSDictionary<?, ?> results) {
				Array<String> recipients = new Array<String>();

				String requestId = "";

				for (Map.Entry<? extends NSObject, ? extends NSObject> entry : results.entrySet()) {
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
			public void didFail(FBSDKGameRequestDialog gameRequestDialog, NSError error) {
				Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Game Request finished with error: " + error.getLocalizedDescription());
				gameRequestCallback.onError(new GDXFacebookError(error.getLocalizedDescription()));
			}

			@Override
			public void didCancel(FBSDKGameRequestDialog gameRequestDialog) {
				Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Game Request has been cancelled.");
				gameRequestCallback.onCancel();
			}
		});

	}

	@Override
	public void signOut() {
		super.signOut();
		loginManager.logOut();
	}

	@Override
	protected void startGUISignIn() {
		Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Starting GUI sign in.");

		VoidBlock2<FBSDKLoginManagerLoginResult, NSError> result = new VoidBlock2<FBSDKLoginManagerLoginResult, NSError>() {

			@Override
			public void invoke(FBSDKLoginManagerLoginResult loginResult, NSError nsError) {
				if (nsError != null) {
					signOut();
					Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Error while trying to sign in: " + nsError.getLocalizedDescription());
					callback.onError(new GDXFacebookError(nsError.getLocalizedDescription()));
				} else if (loginResult.isCancelled()) {
					Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign cancelled by user.");
					signOut();
					callback.onCancel();
				} else {
					accessToken = new GDXFacebookAccessToken(FBSDKAccessToken.getCurrentAccessToken().getTokenString(), FBSDKAccessToken.getCurrentAccessToken().getExpirationDate().toDate().getTime());
					storeNewToken(accessToken);
					Gdx.app.debug(GDXFacebookVars.LOG_TAG, "Sign successful. User token: " + accessToken.getToken());
					callback.onSuccess(new SignInResult(accessToken, "Sign in successful."));
				}

			}

		};

		List<String> listPermissions = new ArrayList<String>();

		for (int i = 0; i < permissions.size; i++) {
			listPermissions.add(permissions.get(i));
		}

		if (this.signInMode == SignInMode.PUBLISH) {
			loginManager.logInWithPublishPermissions(listPermissions, result);
		} else {
			loginManager.logInWithReadPermissions(listPermissions, result);
		}


	}

}

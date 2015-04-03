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

package de.tomgrill.gdxfacebook.desktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import de.tomgrill.gdxfacebook.core.FacebookLoginListener;

public class JXBrowserDesktopFacebookGUI extends Application {

	private static String appId;
	private static String permissions;
	private static FacebookLoginListener listener;

	private static String access_token;
	private static long expirationTimeMillis;
	private static String url;
	private static WebView browser;
	private static WebEngine engine;
	private static StackPane sp;
	private static Scene root;

	private static Stage primaryStage;

	public static void open() {
		if (JXBrowserDesktopFacebookGUI.appId == null || JXBrowserDesktopFacebookGUI.appId.length() == 0) {
			throw new RuntimeException("App ID is empty. Use setAppId() before show()");
		}

		if (JXBrowserDesktopFacebookGUI.permissions == null) {
			throw new RuntimeException("Permissions is empty. Use setPermissions() before show()");
		}
		if (!RunHelper.isStarted) {
			RunHelper.isStarted = true;
			Application.launch(new String());
		}

	}

	@Override
	public void start(Stage primaryStage2) throws Exception {

		Platform.setImplicitExit(false);

		url = "https://www.facebook.com/dialog/oauth?" + "client_id=" + appId + "&redirect_uri=https://www.facebook.com/connect/login_success.html" + "&scope=" + permissions
				+ "&response_type=token";

		primaryStage = primaryStage2;
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setTitle("Facebook Signin");

		browser = new WebView();

		engine = browser.getEngine();

		engine.load(url);
		engine.locationProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldloc, String newLocation) {

				String token_identifier = "access_token=";
				if (newLocation.contains("https://www.facebook.com/connect/login_success.html#access_token=")) {
					access_token = newLocation.substring(newLocation.lastIndexOf(token_identifier) + token_identifier.length(), newLocation.lastIndexOf('&'));
					String expires_in = newLocation.substring(newLocation.lastIndexOf('=') + 1);
					expirationTimeMillis = System.currentTimeMillis() + (Integer.parseInt(expires_in) * 1000);

					passValuesToListener(access_token, expirationTimeMillis);
					closeBrowser();
					return;
				}

				if (newLocation.contains("https://www.facebook.com/connect/login_success.html?error=")) {

					// TODO rework this URL parsing
					String error;
					String errorIdentifier = "error=";

					int amperIndex = 0;
					amperIndex = newLocation.indexOf("&", amperIndex);
					error = newLocation.substring(newLocation.lastIndexOf(errorIdentifier) + errorIdentifier.length(), amperIndex);

					String errorCode;
					String errorCodeIdentifier = "error_code=";
					amperIndex = newLocation.indexOf("&", amperIndex + 1);
					errorCode = newLocation.substring(newLocation.lastIndexOf(errorCodeIdentifier) + errorCodeIdentifier.length(), amperIndex);

					String errorDescription;
					String errorDescriptionIdentifier = "error_description=";
					amperIndex = newLocation.indexOf("&", amperIndex + 1);
					errorDescription = newLocation.substring(newLocation.lastIndexOf(errorDescriptionIdentifier) + errorDescriptionIdentifier.length(), amperIndex);

					String errorReason;
					String errorReasonIdentifier = "error_reason=";
					errorReason = newLocation.substring(newLocation.lastIndexOf(errorReasonIdentifier) + errorReasonIdentifier.length(), newLocation.lastIndexOf('#'));

					JXBrowserDesktopFacebookGUI.listener.onError(error, errorCode, errorDescription, errorReason);

					closeBrowser();

				}
			}

		});

		sp = new StackPane();
		sp.getChildren().add(browser);

		root = new Scene(sp);

		primaryStage.setScene(root);
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				JXBrowserDesktopFacebookGUI.listener.onCancel();
			}
		});
	}

	private void passValuesToListener(String accessToken, long expirationTimeMillis) {
		JXBrowserDesktopFacebookGUI.listener.onSuccess(accessToken, expirationTimeMillis);
	}

	private void closeBrowser() {
		primaryStage.close();
	}

	public static void show(FacebookLoginListener listener) {
		JXBrowserDesktopFacebookGUI.listener = listener;

		open();
	}

	public static void setAppId(String appId) {
		JXBrowserDesktopFacebookGUI.appId = appId;

	}

	public String getAppId() {
		return JXBrowserDesktopFacebookGUI.appId;
	}

	public String getPermissions() {
		return permissions;
	}

	public static void setPermissions(String permissions) {
		JXBrowserDesktopFacebookGUI.permissions = permissions;
	}

	public static void reuse() {
		engine.load(url);
		primaryStage.show();

	}

}

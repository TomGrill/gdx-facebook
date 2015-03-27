package de.tpronold.gdxfacebook.desktop;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class JXBrowserDesktopFacebookGUI extends Application implements DesktopFacebookGUI {

	private String appId;
	private String permissions;
	private String access_token;
	private long expirationTimeMillis;
	private String url;
	private WebView browser;
	private WebEngine engine;
	private StackPane sp;
	private Scene root;

	private Stage primaryStage;

	private FacebookLoginListener listener;

	public void open() {

		if (appId == null || appId.length() == 0) {
			throw new RuntimeException("App ID is empty. Use setAppId() before show()");
		}

		if (permissions == null) {
			throw new RuntimeException("Permissions is empty. Use setPermissions() before show()");
		}

		url = "https://www.facebook.com/dialog/oauth?" + "client_id=" + appId + "&redirect_uri=https://www.facebook.com/connect/login_success.html" + "&scope=" + permissions
				+ "&response_type=token";

		Application.launch(new String());

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;

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

					JXBrowserDesktopFacebookGUI.this.listener.onError(error, errorCode, errorDescription, errorReason);

					closeBrowser();

				}
			}

		});

		sp = new StackPane();
		sp.getChildren().add(browser);

		root = new Scene(sp);

		this.primaryStage.setScene(root);
		this.primaryStage.show();

	}

	private void passValuesToListener(String accessToken, long expirationTimeMillis) {
		this.listener.onSuccess(accessToken, expirationTimeMillis);
	}

	private void closeBrowser() {
		primaryStage.close();
	}

	@Override
	public void show(FacebookLoginListener listener) {
		this.listener = listener;

		open();
	}

	@Override
	public void setAppId(String appId) {
		this.appId = appId;

	}

	@Override
	public String getAppId() {
		return this.appId;
	}

	@Override
	public String getPermissions() {
		return permissions;
	}

	@Override
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

}

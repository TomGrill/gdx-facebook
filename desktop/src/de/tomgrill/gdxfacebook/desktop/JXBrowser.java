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

import com.badlogic.gdx.utils.Array;

import java.net.CookieHandler;
import java.net.CookieManager;

import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.utils.Utils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JXBrowser extends Application {

    private static final String LAUNCH_PARAMETERS = "";

    private static boolean hasBeenLaunched = false;

    private static JXBrowserCallbackHandler callbackHandler;

    private static String appId;
    private static String permissions;
    private static String url;

    private static WebView webView;
    private static WebEngine webEngine;
    private static Stage primaryStage;
    private static StackPane stackPane;
    private static Scene rootScene;


    private static void showGUILogin() {
        if (!hasBeenLaunched) {
            hasBeenLaunched = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    launchApplication();
                }
            }).start();

        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    reuseApplication();
                }
            });

        }
    }

    private static void launchApplication() {
        JXBrowser.launch(LAUNCH_PARAMETERS);
    }

    private static void reuseApplication() {
        CookieHandler.setDefault(new CookieManager());
        webEngine.loadContent("");
        webEngine.load(url);
        primaryStage.show();
    }


    @Override
    public void start(Stage pStage) throws Exception {

        Platform.setImplicitExit(false);

        primaryStage = pStage;
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Facebook Sign in");

        webView = new WebView();

        CookieHandler.setDefault(new CookieManager());

        webEngine = webView.getEngine();

//        webEngine.getLoadWorker().stateProperty().addListener(
//                new ChangeListener<Worker.State>() {
//                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
//                        if (webEngine.getLoadWorker().getException() != null && newState == Worker.State.FAILED){
//                            System.out.println(webEngine.getLoadWorker().getException().toString());
//                        }
//                    }
//                });

        webEngine.load(url);
        webEngine.locationProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String oldLocation, String newLocation) {

                /**
                 *  Return to DesktopGDXFacebook when we receive a error or successful login in URL change.
                 *  (not unit tested)
                 * */
                if (Utils.isValidSuccessfulSignInURL(newLocation) || Utils.isValidErrorSignInURL(newLocation)) {
                    callbackHandler.handleURL(newLocation);
                    closeBrowser();
                }
            }

        });


        stackPane = new StackPane();
        stackPane.getChildren().add(webView);

        rootScene = new Scene(stackPane);

        primaryStage.setScene(rootScene);
        primaryStage.show();


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                JXBrowser.callbackHandler.handleCancel();
            }
        });
    }


    private void closeBrowser() {
        primaryStage.close();
    }

    public static void setAppId(String app_id) {
        JXBrowser.appId = app_id;
    }

    public static void setPermissions(Array<String> permissions) {
        JXBrowser.permissions = Utils.permissionsArrayToString(permissions);
    }

    public static void login(Array<String> permissions, GDXFacebookConfig config, JXBrowserCallbackHandler handler) {
        setAppId(config.APP_ID);
        setPermissions(permissions);
        JXBrowser.callbackHandler = handler;
        buildURL();
        showGUILogin();
    }

    private static void buildURL() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("https://www.facebook.com/dialog/oauth?");
        stringBuffer.append("client_id=");
        stringBuffer.append(appId);
        stringBuffer.append("&redirect_uri=https://www.facebook.com/connect/login_success.html");
        stringBuffer.append("&scope=");
        stringBuffer.append(permissions);
        stringBuffer.append("&response_type=token");
        url = stringBuffer.toString();
        System.out.println(url);
    }
}

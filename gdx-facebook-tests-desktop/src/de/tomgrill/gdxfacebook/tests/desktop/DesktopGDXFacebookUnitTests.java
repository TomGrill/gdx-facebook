package de.tomgrill.gdxfacebook.tests.desktop;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GraphError;
import de.tomgrill.gdxfacebook.core.JsonResult;
import de.tomgrill.gdxfacebook.core.LoginResult;
import de.tomgrill.gdxfacebook.core.Result;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.desktop.DesktopGDXFacebook;
import de.tomgrill.gdxfacebook.desktop.JXBrowser;
import de.tomgrill.gdxfacebook.desktop.JXBrowserCallbackHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JXBrowser.class})
public class DesktopGDXFacebookUnitTests {

    private static String ACCESS_TOKEN = "CAAWKPADhZD";
    private static int EXPIRES_IN = 5184000;

    private static String ERROR_URL = "https://www.facebook.com/connect/login_success.html?error=access_denied&error_code=200&error_description=Permissions+error&error_reason=user_denied#_=_";
    private static String SUCCESS_URL = "https://www.facebook.com/connect/login_success.html#access_token=" + ACCESS_TOKEN + "&expires_in=" + EXPIRES_IN;

    private boolean testDidSucceed_1;
    private boolean testDidSucceed_2;

    DesktopGDXFacebook fixture;

    Array<String> permissions;
    GDXFacebookCallback mockCallback;
    GDXFacebookConfig config;
    JXBrowserCallbackHandler mockCallbackHandler;

    @Before
    public void setup() {
        config = new GDXFacebookConfig();
        fixture = new DesktopGDXFacebook(config);

        Array<String> permissions = new Array<String>();
        permissions.add("public_profile");
        permissions.add("user_friends");
        permissions.add("email");

        mockCallback = mock(GDXFacebookCallback.class);
        mockCallbackHandler = mock(JXBrowserCallbackHandler.class);

        mockStatic(JXBrowser.class);
    }

    @Test
    public void signInAndCancel() {


        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                fixture.onCancel();
                return null;
            }
        }).when(JXBrowser.class);
        JXBrowser.login(permissions, config, fixture);

        fixture.signIn(SignInMode.READ, permissions, mockCallback);

        verify(mockCallback).onCancel();
    }

    @Test
    public void signInAndError() {
        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                fixture.handleURL(ERROR_URL);
                return null;
            }
        }).when(JXBrowser.class);
        JXBrowser.login(permissions, config, fixture);

        fixture.signIn(SignInMode.READ, permissions, mockCallback);

        verify(mockCallback).onError(any(GraphError.class));
    }


    @Test
    public void signInAndErrorWithMessage() {
        testDidSucceed_1 = false;

        GDXFacebookCallback callback = new GDXFacebookCallback() {
            @Override
            public void onSuccess(Result result) {

            }

            @Override
            public void onError(GraphError error) {
                if (error.getErrorMesssage().equals("error=access_denied&error_code=200&error_description=Permissions+error&error_reason=user_denied")) {
                    testDidSucceed_1 = true;
                }
            }

            @Override
            public void onFail(Throwable t) {

            }

            @Override
            public void onCancel() {

            }
        };

        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                fixture.handleURL(ERROR_URL);
                return null;
            }
        }).when(JXBrowser.class);
        JXBrowser.login(permissions, config, fixture);

        fixture.signIn(SignInMode.READ, permissions, callback);

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(testDidSucceed_1);
    }

    @Test
    public void signInAndSuccess() {
        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                fixture.handleURL(SUCCESS_URL);
                return null;
            }
        }).when(JXBrowser.class);
        JXBrowser.login(permissions, config, fixture);

        fixture.signIn(SignInMode.READ, permissions, mockCallback);

        verify(mockCallback).onSuccess(any(JsonResult.class));
    }

    @Test
    public void signInAndSuccessWithGraphResult() {

        testDidSucceed_2 = false;

        GDXFacebookCallback callback = new GDXFacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                testDidSucceed_2 = true;
                assertEquals(ACCESS_TOKEN, result.getAccessToken().getToken());
                assertEquals(EXPIRES_IN, result.getAccessToken().getExpiresIn());
            }

            @Override
            public void onError(GraphError error) {

            }

            @Override
            public void onFail(Throwable t) {

            }

            @Override
            public void onCancel() {

            }
        };

        PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                fixture.handleURL(SUCCESS_URL);
                return null;
            }
        }).when(JXBrowser.class);
        JXBrowser.login(permissions, config, fixture);

        fixture.signIn(SignInMode.READ, permissions, callback);

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(testDidSucceed_2);
    }
}

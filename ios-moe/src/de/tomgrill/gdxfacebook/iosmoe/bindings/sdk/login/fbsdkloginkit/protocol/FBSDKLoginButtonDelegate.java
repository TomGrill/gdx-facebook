package de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.login.fbsdkloginkit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IsOptional;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSError;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.login.fbsdkloginkit.FBSDKLoginButton;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.login.fbsdkloginkit.FBSDKLoginManagerLoginResult;

@Generated
@Library("FBSDKLoginKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKLoginButtonDelegate")
public interface FBSDKLoginButtonDelegate {
	@Generated
	@Selector("loginButton:didCompleteWithResult:error:")
	void loginButtonDidCompleteWithResultError(FBSDKLoginButton loginButton,
			FBSDKLoginManagerLoginResult result, NSError error);

	@Generated
	@Selector("loginButtonDidLogOut:")
	void loginButtonDidLogOut(FBSDKLoginButton loginButton);

	@Generated
	@IsOptional
	@Selector("loginButtonWillLogin:")
	default boolean loginButtonWillLogin(FBSDKLoginButton loginButton) {
		throw new java.lang.UnsupportedOperationException();
	}
}
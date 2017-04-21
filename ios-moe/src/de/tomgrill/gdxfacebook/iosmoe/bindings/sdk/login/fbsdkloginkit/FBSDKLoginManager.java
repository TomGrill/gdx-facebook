package de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.login.fbsdkloginkit;


import org.moe.natj.c.ann.FunctionPtr;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.NInt;
import org.moe.natj.general.ann.NUInt;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.VoidPtr;
import org.moe.natj.objc.Class;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.SEL;
import org.moe.natj.objc.ann.ObjCBlock;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.uikit.UIViewController;

@Generated
@Library("FBSDKLoginKit")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FBSDKLoginManager extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FBSDKLoginManager(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FBSDKLoginManager alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("cancelPreviousPerformRequestsWithTarget:")
	public static native void cancelPreviousPerformRequestsWithTarget(
			@Mapped(ObjCObjectMapper.class) Object aTarget);

	@Generated
	@Selector("cancelPreviousPerformRequestsWithTarget:selector:object:")
	public static native void cancelPreviousPerformRequestsWithTargetSelectorObject(
			@Mapped(ObjCObjectMapper.class) Object aTarget, SEL aSelector,
			@Mapped(ObjCObjectMapper.class) Object anArgument);

	@Generated
	@Selector("class")
	public static native Class class_objc_static();

	@Generated
	@Selector("classFallbacksForKeyedArchiver")
	public static native NSArray<String> classFallbacksForKeyedArchiver();

	@Generated
	@Selector("classForKeyedUnarchiver")
	public static native Class classForKeyedUnarchiver();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("defaultAudience")
	@NUInt
	public native long defaultAudience();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FBSDKLoginManager init();

	@Generated
	@Selector("initialize")
	public static native void initialize();

	@Generated
	@Selector("instanceMethodForSelector:")
	@FunctionPtr(name = "call_instanceMethodForSelector_ret")
	public static native NSObject.Function_instanceMethodForSelector_ret instanceMethodForSelector(
			SEL aSelector);

	@Generated
	@Selector("instanceMethodSignatureForSelector:")
	public static native NSMethodSignature instanceMethodSignatureForSelector(
			SEL aSelector);

	@Generated
	@Selector("instancesRespondToSelector:")
	public static native boolean instancesRespondToSelector(SEL aSelector);

	@Generated
	@Selector("isSubclassOfClass:")
	public static native boolean isSubclassOfClass(Class aClass);

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Selector("load")
	public static native void load_objc_static();

	@Generated
	@Selector("logInWithPublishPermissions:fromViewController:handler:")
	public native void logInWithPublishPermissionsFromViewControllerHandler(
			NSArray<?> permissions,
			UIViewController fromViewController,
			@ObjCBlock(name = "call_logInWithPublishPermissionsFromViewControllerHandler") Block_logInWithPublishPermissionsFromViewControllerHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_logInWithPublishPermissionsFromViewControllerHandler {
		@Generated
		void call_logInWithPublishPermissionsFromViewControllerHandler(
				FBSDKLoginManagerLoginResult arg0, NSError arg1);
	}

	@Generated
	@Deprecated
	@Selector("logInWithPublishPermissions:handler:")
	public native void logInWithPublishPermissionsHandler(
			NSArray<?> permissions,
			@ObjCBlock(name = "call_logInWithPublishPermissionsHandler") Block_logInWithPublishPermissionsHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_logInWithPublishPermissionsHandler {
		@Generated
		void call_logInWithPublishPermissionsHandler(
				FBSDKLoginManagerLoginResult arg0, NSError arg1);
	}

	@Generated
	@Selector("logInWithReadPermissions:fromViewController:handler:")
	public native void logInWithReadPermissionsFromViewControllerHandler(
			NSArray<?> permissions,
			UIViewController fromViewController,
			@ObjCBlock(name = "call_logInWithReadPermissionsFromViewControllerHandler") Block_logInWithReadPermissionsFromViewControllerHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_logInWithReadPermissionsFromViewControllerHandler {
		@Generated
		void call_logInWithReadPermissionsFromViewControllerHandler(
				FBSDKLoginManagerLoginResult arg0, NSError arg1);
	}

	@Generated
	@Deprecated
	@Selector("logInWithReadPermissions:handler:")
	public native void logInWithReadPermissionsHandler(
			NSArray<?> permissions,
			@ObjCBlock(name = "call_logInWithReadPermissionsHandler") Block_logInWithReadPermissionsHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_logInWithReadPermissionsHandler {
		@Generated
		void call_logInWithReadPermissionsHandler(
				FBSDKLoginManagerLoginResult arg0, NSError arg1);
	}

	@Generated
	@Selector("logOut")
	public native void logOut();

	@Generated
	@Selector("loginBehavior")
	@NUInt
	public native long loginBehavior();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("renewSystemCredentials:")
	public static native void renewSystemCredentials(
			@ObjCBlock(name = "call_renewSystemCredentials") Block_renewSystemCredentials handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_renewSystemCredentials {
		@Generated
		void call_renewSystemCredentials(@NInt long arg0, NSError arg1);
	}

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setDefaultAudience:")
	public native void setDefaultAudience(@NUInt long value);

	@Generated
	@Selector("setLoginBehavior:")
	public native void setLoginBehavior(@NUInt long value);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}
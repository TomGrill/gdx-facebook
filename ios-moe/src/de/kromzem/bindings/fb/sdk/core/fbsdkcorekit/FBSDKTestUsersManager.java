package de.kromzem.bindings.fb.sdk.core.fbsdkcorekit;


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

@Generated
@Library("FBSDKCoreKit")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FBSDKTestUsersManager extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FBSDKTestUsersManager(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Selector("addTestAccountWithPermissions:completionHandler:")
	public native void addTestAccountWithPermissionsCompletionHandler(
			NSSet<?> permissions,
			@ObjCBlock(name = "call_addTestAccountWithPermissionsCompletionHandler") Block_addTestAccountWithPermissionsCompletionHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_addTestAccountWithPermissionsCompletionHandler {
		@Generated
		void call_addTestAccountWithPermissionsCompletionHandler(
				NSArray<?> arg0, NSError arg1);
	}

	@Generated
	@Owned
	@Selector("alloc")
	public static native FBSDKTestUsersManager alloc();

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
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FBSDKTestUsersManager init();

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
	@Selector("makeFriendsWithFirst:second:callback:")
	public native void makeFriendsWithFirstSecondCallback(
			FBSDKAccessToken first,
			FBSDKAccessToken second,
			@ObjCBlock(name = "call_makeFriendsWithFirstSecondCallback") Block_makeFriendsWithFirstSecondCallback callback);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_makeFriendsWithFirstSecondCallback {
		@Generated
		void call_makeFriendsWithFirstSecondCallback(NSError arg0);
	}

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("removeTestAccount:completionHandler:")
	public native void removeTestAccountCompletionHandler(
			String userId,
			@ObjCBlock(name = "call_removeTestAccountCompletionHandler") Block_removeTestAccountCompletionHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_removeTestAccountCompletionHandler {
		@Generated
		void call_removeTestAccountCompletionHandler(NSError arg0);
	}

	@Generated
	@Selector("requestTestAccountTokensWithArraysOfPermissions:createIfNotFound:completionHandler:")
	public native void requestTestAccountTokensWithArraysOfPermissionsCreateIfNotFoundCompletionHandler(
			NSArray<?> arraysOfPermissions,
			boolean createIfNotFound,
			@ObjCBlock(name = "call_requestTestAccountTokensWithArraysOfPermissionsCreateIfNotFoundCompletionHandler") Block_requestTestAccountTokensWithArraysOfPermissionsCreateIfNotFoundCompletionHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_requestTestAccountTokensWithArraysOfPermissionsCreateIfNotFoundCompletionHandler {
		@Generated
		void call_requestTestAccountTokensWithArraysOfPermissionsCreateIfNotFoundCompletionHandler(
				NSArray<?> arg0, NSError arg1);
	}

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("sharedInstanceForAppID:appSecret:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object sharedInstanceForAppIDAppSecret(String appID,
			String appSecret);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}
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
import apple.foundation.NSData;
import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSNumber;
import apple.foundation.NSSet;

@Generated
@Library("FBSDKCoreKit")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FBSDKAppEvents extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FBSDKAppEvents(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Selector("activateApp")
	public static native void activateApp();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FBSDKAppEvents alloc();

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
	@Selector("flush")
	public static native void flush();

	@Generated
	@Selector("flushBehavior")
	@NUInt
	public static native long flushBehavior();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FBSDKAppEvents init();

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
	@Selector("logEvent:")
	public static native void logEvent(String eventName);

	@Generated
	@Selector("logEvent:parameters:")
	public static native void logEventParameters(String eventName,
			NSDictionary<?, ?> parameters);

	@Generated
	@Selector("logEvent:valueToSum:")
	public static native void logEventValueToSum(String eventName,
			double valueToSum);

	@Generated
	@Selector("logEvent:valueToSum:parameters:")
	public static native void logEventValueToSumParameters(String eventName,
			double valueToSum, NSDictionary<?, ?> parameters);

	@Generated
	@Selector("logEvent:valueToSum:parameters:accessToken:")
	public static native void logEventValueToSumParametersAccessToken(
			String eventName, NSNumber valueToSum,
			NSDictionary<?, ?> parameters, FBSDKAccessToken accessToken);

	@Generated
	@Selector("logPurchase:currency:")
	public static native void logPurchaseCurrency(double purchaseAmount,
			String currency);

	@Generated
	@Selector("logPurchase:currency:parameters:")
	public static native void logPurchaseCurrencyParameters(
			double purchaseAmount, String currency,
			NSDictionary<?, ?> parameters);

	@Generated
	@Selector("logPurchase:currency:parameters:accessToken:")
	public static native void logPurchaseCurrencyParametersAccessToken(
			double purchaseAmount, String currency,
			NSDictionary<?, ?> parameters, FBSDKAccessToken accessToken);

	@Generated
	@Selector("logPushNotificationOpen:")
	public static native void logPushNotificationOpen(NSDictionary<?, ?> payload);

	@Generated
	@Selector("logPushNotificationOpen:action:")
	public static native void logPushNotificationOpenAction(
			NSDictionary<?, ?> payload, String action);

	@Generated
	@Selector("loggingOverrideAppID")
	public static native String loggingOverrideAppID();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("requestForCustomAudienceThirdPartyIDWithAccessToken:")
	public static native FBSDKGraphRequest requestForCustomAudienceThirdPartyIDWithAccessToken(
			FBSDKAccessToken accessToken);

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setFlushBehavior:")
	public static native void setFlushBehavior(@NUInt long flushBehavior);

	@Generated
	@Selector("setLoggingOverrideAppID:")
	public static native void setLoggingOverrideAppID(String appID);

	@Generated
	@Selector("setPushNotificationsDeviceToken:")
	public static native void setPushNotificationsDeviceToken(NSData deviceToken);

	@Generated
	@Selector("setUserID:")
	public static native void setUserID(String userID);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("updateUserProperties:handler:")
	public static native void updateUserPropertiesHandler(
			NSDictionary<?, ?> properties,
			@ObjCBlock(name = "call_updateUserPropertiesHandler") Block_updateUserPropertiesHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_updateUserPropertiesHandler {
		@Generated
		void call_updateUserPropertiesHandler(FBSDKGraphRequestConnection arg0,
				@Mapped(ObjCObjectMapper.class) Object arg1, NSError arg2);
	}

	@Generated
	@Selector("userID")
	public static native String userID();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}
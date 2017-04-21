package de.kromzem.bindings.fb.sdk.core.fbsdkcorekit;


import org.moe.natj.c.ann.FunctionPtr;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.NFloat;
import org.moe.natj.general.ann.NInt;
import org.moe.natj.general.ann.NUInt;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.VoidPtr;
import org.moe.natj.objc.Class;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.SEL;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSNumber;
import apple.foundation.NSSet;

@Generated
@Library("FBSDKCoreKit")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FBSDKSettings extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FBSDKSettings(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("JPEGCompressionQuality")
	@NFloat
	public static native double JPEGCompressionQuality();

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FBSDKSettings alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("appID")
	public static native String appID();

	@Generated
	@Selector("appURLSchemeSuffix")
	public static native String appURLSchemeSuffix();

	@Generated
	@Selector("autoLogAppEventsEnabled")
	public static native NSNumber autoLogAppEventsEnabled();

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
	@Selector("clientToken")
	public static native String clientToken();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("disableLoggingBehavior:")
	public static native void disableLoggingBehavior(String loggingBehavior);

	@Generated
	@Selector("displayName")
	public static native String displayName();

	@Generated
	@Selector("enableLoggingBehavior:")
	public static native void enableLoggingBehavior(String loggingBehavior);

	@Generated
	@Selector("facebookDomainPart")
	public static native String facebookDomainPart();

	@Generated
	@Selector("graphAPIVersion")
	public static native String graphAPIVersion();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FBSDKSettings init();

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
	@Selector("legacyUserDefaultTokenInformationKeyName")
	public static native String legacyUserDefaultTokenInformationKeyName();

	@Generated
	@Selector("limitEventAndDataUsage")
	public static native boolean limitEventAndDataUsage();

	@Generated
	@Selector("load")
	public static native void load_objc_static();

	@Generated
	@Selector("loggingBehavior")
	public static native NSSet<?> loggingBehavior();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("sdkVersion")
	public static native String sdkVersion();

	@Generated
	@Selector("setAppID:")
	public static native void setAppID(String appID);

	@Generated
	@Selector("setAppURLSchemeSuffix:")
	public static native void setAppURLSchemeSuffix(String appURLSchemeSuffix);

	@Generated
	@Selector("setAutoLogAppEventsEnabled:")
	public static native void setAutoLogAppEventsEnabled(
			NSNumber AutoLogAppEventsEnabled);

	@Generated
	@Selector("setClientToken:")
	public static native void setClientToken(String clientToken);

	@Generated
	@Selector("setDisplayName:")
	public static native void setDisplayName(String displayName);

	@Generated
	@Selector("setFacebookDomainPart:")
	public static native void setFacebookDomainPart(String facebookDomainPart);

	@Generated
	@Selector("setGraphAPIVersion:")
	public static native void setGraphAPIVersion(String version);

	@Generated
	@Selector("setGraphErrorRecoveryDisabled:")
	public static native void setGraphErrorRecoveryDisabled(
			boolean disableGraphErrorRecovery);

	@Generated
	@Selector("setJPEGCompressionQuality:")
	public static native void setJPEGCompressionQuality(
			@NFloat double JPEGCompressionQuality);

	@Generated
	@Selector("setLegacyUserDefaultTokenInformationKeyName:")
	public static native void setLegacyUserDefaultTokenInformationKeyName(
			String tokenInformationKeyName);

	@Generated
	@Selector("setLimitEventAndDataUsage:")
	public static native void setLimitEventAndDataUsage(
			boolean limitEventAndDataUsage);

	@Generated
	@Selector("setLoggingBehavior:")
	public static native void setLoggingBehavior(NSSet<?> loggingBehavior);

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
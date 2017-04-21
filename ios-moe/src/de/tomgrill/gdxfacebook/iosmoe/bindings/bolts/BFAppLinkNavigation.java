package de.tomgrill.gdxfacebook.iosmoe.bindings.bolts;


import de.tomgrill.gdxfacebook.iosmoe.bindings.bolts.protocol.BFAppLinkResolving;
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
import org.moe.natj.general.ann.ReferenceInfo;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.Ptr;
import org.moe.natj.general.ptr.VoidPtr;
import org.moe.natj.objc.Class;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.SEL;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.foundation.NSURL;

@Generated
@Library("Bolts")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class BFAppLinkNavigation extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected BFAppLinkNavigation(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native BFAppLinkNavigation alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("appLink")
	public native BFAppLink appLink();

	@Generated
	@Selector("appLinkData")
	public native NSDictionary<?, ?> appLinkData();

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("callbackAppLinkDataForAppWithName:url:")
	public static native NSDictionary<?, ?> callbackAppLinkDataForAppWithNameUrl(
			String appName, String url);

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
	@Selector("defaultResolver")
	@MappedReturn(ObjCObjectMapper.class)
	public static native BFAppLinkResolving defaultResolver();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("extras")
	public native NSDictionary<?, ?> extras();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native BFAppLinkNavigation init();

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
	@Selector("navigate:")
	@NInt
	public native long navigate(
			@ReferenceInfo(type = NSError.class) Ptr<NSError> error);

	@Generated
	@Selector("navigateToAppLink:error:")
	@NInt
	public static native long navigateToAppLinkError(BFAppLink link,
			@ReferenceInfo(type = NSError.class) Ptr<NSError> error);

	@Generated
	@Selector("navigateToURLInBackground:")
	public static native BFTask<?> navigateToURLInBackground(NSURL destination);

	@Generated
	@Selector("navigateToURLInBackground:resolver:")
	public static native BFTask<?> navigateToURLInBackgroundResolver(
			NSURL destination,
			@Mapped(ObjCObjectMapper.class) BFAppLinkResolving resolver);

	@Generated
	@Selector("navigationType")
	@NInt
	public native long navigationType();

	@Generated
	@Selector("navigationTypeForLink:")
	@NInt
	public static native long navigationTypeForLink(BFAppLink link);

	@Generated
	@Selector("navigationWithAppLink:extras:appLinkData:")
	public static native BFAppLinkNavigation navigationWithAppLinkExtrasAppLinkData(
			BFAppLink appLink, NSDictionary<?, ?> extras,
			NSDictionary<?, ?> appLinkData);

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("resolveAppLinkInBackground:")
	public static native BFTask<?> resolveAppLinkInBackground(NSURL destination);

	@Generated
	@Selector("resolveAppLinkInBackground:resolver:")
	public static native BFTask<?> resolveAppLinkInBackgroundResolver(
			NSURL destination,
			@Mapped(ObjCObjectMapper.class) BFAppLinkResolving resolver);

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setDefaultResolver:")
	public static native void setDefaultResolver(
			@Mapped(ObjCObjectMapper.class) BFAppLinkResolving resolver);

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
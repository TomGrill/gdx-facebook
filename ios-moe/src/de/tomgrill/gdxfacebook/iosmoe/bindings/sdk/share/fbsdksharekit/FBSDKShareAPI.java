package de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit;


import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.protocol.FBSDKSharing;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.protocol.FBSDKSharingContent;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.protocol.FBSDKSharingDelegate;
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
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.core.fbsdkcorekit.FBSDKAccessToken;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FBSDKShareAPI extends NSObject implements FBSDKSharing {
	static {
		NatJ.register();
	}

	@Generated
	protected FBSDKShareAPI(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Selector("accessToken")
	public native FBSDKAccessToken accessToken();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FBSDKShareAPI alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("canShare")
	public native boolean canShare();

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
	@Selector("createOpenGraphObject:")
	public native boolean createOpenGraphObject(
			FBSDKShareOpenGraphObject openGraphObject);

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("delegate")
	@MappedReturn(ObjCObjectMapper.class)
	public native Object delegate();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("graphNode")
	public native String graphNode();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FBSDKShareAPI init();

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
	@Selector("message")
	public native String message();

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
	@Selector("setAccessToken:")
	public native void setAccessToken(FBSDKAccessToken value);

	@Generated
	@Selector("setDelegate:")
	public native void setDelegate_unsafe(
			@Mapped(ObjCObjectMapper.class) Object value);

	@Generated
	public void setDelegate(@Mapped(ObjCObjectMapper.class) Object value) {
		Object __old = delegate();
		if (value != null) {
			org.moe.natj.objc.ObjCRuntime.associateObjCObject(this, value);
		}
		setDelegate_unsafe(value);
		if (__old != null) {
			org.moe.natj.objc.ObjCRuntime.dissociateObjCObject(this, __old);
		}
	}

	@Generated
	@Selector("setGraphNode:")
	public native void setGraphNode(String value);

	@Generated
	@Selector("setMessage:")
	public native void setMessage(String value);

	@Generated
	@Selector("setShareContent:")
	public native void setShareContent(
			@Mapped(ObjCObjectMapper.class) Object value);

	@Generated
	@Selector("setShouldFailOnDataError:")
	public native void setShouldFailOnDataError(boolean value);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("share")
	public native boolean share();

	@Generated
	@Selector("shareContent")
	@MappedReturn(ObjCObjectMapper.class)
	public native Object shareContent();

	@Generated
	@Selector("shareWithContent:delegate:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object shareWithContentDelegate(
			@Mapped(ObjCObjectMapper.class) FBSDKSharingContent content,
			@Mapped(ObjCObjectMapper.class) FBSDKSharingDelegate delegate);

	@Generated
	@Selector("shouldFailOnDataError")
	public native boolean shouldFailOnDataError();

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("validateWithError:")
	public native boolean validateWithError(
			@ReferenceInfo(type = NSError.class) Ptr<NSError> errorRef);

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}
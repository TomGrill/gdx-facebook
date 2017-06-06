package de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit;


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
import org.moe.natj.objc.ann.ProtocolClassMethod;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSCoder;
import apple.foundation.NSDictionary;
import apple.foundation.NSEnumerator;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSNumber;
import apple.foundation.NSSet;
import apple.foundation.NSURL;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.protocol.FBSDKShareOpenGraphValueContaining;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FBSDKShareOpenGraphValueContainer extends NSObject implements
		FBSDKShareOpenGraphValueContaining {
	static {
		NatJ.register();
	}

	@Generated
	protected FBSDKShareOpenGraphValueContainer(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("URLForKey:")
	public native NSURL URLForKey(String key);

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FBSDKShareOpenGraphValueContainer alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("arrayForKey:")
	public native NSArray<?> arrayForKey(String key);

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
	@Selector("encodeWithCoder:")
	public native void encodeWithCoder(NSCoder aCoder);

	@Generated
	@Selector("enumerateKeysAndObjectsUsingBlock:")
	public native void enumerateKeysAndObjectsUsingBlock(
			@ObjCBlock(name = "call_enumerateKeysAndObjectsUsingBlock") FBSDKShareOpenGraphValueContaining.Block_enumerateKeysAndObjectsUsingBlock block);

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FBSDKShareOpenGraphValueContainer init();

	@Generated
	@Selector("initWithCoder:")
	public native FBSDKShareOpenGraphValueContainer initWithCoder(
			NSCoder aDecoder);

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
	@Selector("keyEnumerator")
	public native NSEnumerator<?> keyEnumerator();

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Selector("load")
	public static native void load_objc_static();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("numberForKey:")
	public native NSNumber numberForKey(String key);

	@Generated
	@Selector("objectEnumerator")
	public native NSEnumerator<?> objectEnumerator();

	@Generated
	@Selector("objectForKey:")
	public native FBSDKShareOpenGraphObject objectForKey(String key);

	@Generated
	@Selector("objectForKeyedSubscript:")
	@MappedReturn(ObjCObjectMapper.class)
	public native Object objectForKeyedSubscript(String key);

	@Generated
	@Selector("parseProperties:")
	public native void parseProperties(NSDictionary<?, ?> properties);

	@Generated
	@Selector("photoForKey:")
	public native FBSDKSharePhoto photoForKey(String key);

	@Generated
	@Selector("removeObjectForKey:")
	public native void removeObjectForKey(String key);

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setArray:forKey:")
	public native void setArrayForKey(NSArray<?> array, String key);

	@Generated
	@Selector("setNumber:forKey:")
	public native void setNumberForKey(NSNumber number, String key);

	@Generated
	@Selector("setObject:forKey:")
	public native void setObjectForKey(FBSDKShareOpenGraphObject object,
			String key);

	@Generated
	@Selector("setPhoto:forKey:")
	public native void setPhotoForKey(FBSDKSharePhoto photo, String key);

	@Generated
	@Selector("setString:forKey:")
	public native void setStringForKey(String string, String key);

	@Generated
	@Selector("setURL:forKey:")
	public native void setURLForKey(NSURL URL, String key);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("stringForKey:")
	public native String stringForKey(String key);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("supportsSecureCoding")
	public static native boolean supportsSecureCoding();

	@Generated
	@ProtocolClassMethod("supportsSecureCoding")
	public boolean _supportsSecureCoding() {
		return supportsSecureCoding();
	}

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}
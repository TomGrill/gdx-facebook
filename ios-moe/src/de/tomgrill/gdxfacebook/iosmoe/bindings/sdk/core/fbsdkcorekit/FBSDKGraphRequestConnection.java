package de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.core.fbsdkcorekit;


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
import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import apple.foundation.NSHTTPURLResponse;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSOperationQueue;
import apple.foundation.NSSet;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.core.fbsdkcorekit.protocol.FBSDKGraphRequestConnectionDelegate;

@Generated
@Library("FBSDKCoreKit")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FBSDKGraphRequestConnection extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FBSDKGraphRequestConnection(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("URLResponse")
	public native NSHTTPURLResponse URLResponse();

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Selector("addRequest:completionHandler:")
	public native void addRequestCompletionHandler(
			FBSDKGraphRequest request,
			@ObjCBlock(name = "call_addRequestCompletionHandler") Block_addRequestCompletionHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_addRequestCompletionHandler {
		@Generated
		void call_addRequestCompletionHandler(FBSDKGraphRequestConnection arg0,
				@Mapped(ObjCObjectMapper.class) Object arg1, NSError arg2);
	}

	@Generated
	@Selector("addRequest:completionHandler:batchEntryName:")
	public native void addRequestCompletionHandlerBatchEntryName(
			FBSDKGraphRequest request,
			@ObjCBlock(name = "call_addRequestCompletionHandlerBatchEntryName") Block_addRequestCompletionHandlerBatchEntryName handler,
			String name);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_addRequestCompletionHandlerBatchEntryName {
		@Generated
		void call_addRequestCompletionHandlerBatchEntryName(
				FBSDKGraphRequestConnection arg0,
				@Mapped(ObjCObjectMapper.class) Object arg1, NSError arg2);
	}

	@Generated
	@Selector("addRequest:completionHandler:batchParameters:")
	public native void addRequestCompletionHandlerBatchParameters(
			FBSDKGraphRequest request,
			@ObjCBlock(name = "call_addRequestCompletionHandlerBatchParameters") Block_addRequestCompletionHandlerBatchParameters handler,
			NSDictionary<?, ?> batchParameters);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_addRequestCompletionHandlerBatchParameters {
		@Generated
		void call_addRequestCompletionHandlerBatchParameters(
				FBSDKGraphRequestConnection arg0,
				@Mapped(ObjCObjectMapper.class) Object arg1, NSError arg2);
	}

	@Generated
	@Owned
	@Selector("alloc")
	public static native FBSDKGraphRequestConnection alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("cancel")
	public native void cancel();

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
	@Selector("delegate")
	@MappedReturn(ObjCObjectMapper.class)
	public native FBSDKGraphRequestConnectionDelegate delegate();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FBSDKGraphRequestConnection init();

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
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("overrideVersionPartWith:")
	public native void overrideVersionPartWith(String version);

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setDefaultConnectionTimeout:")
	public static native void setDefaultConnectionTimeout(
			double defaultConnectionTimeout);

	@Generated
	@Selector("setDelegate:")
	public native void setDelegate_unsafe(
			@Mapped(ObjCObjectMapper.class) FBSDKGraphRequestConnectionDelegate value);

	@Generated
	public void setDelegate(
			@Mapped(ObjCObjectMapper.class) FBSDKGraphRequestConnectionDelegate value) {
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
	@Selector("setDelegateQueue:")
	public native void setDelegateQueue(NSOperationQueue queue);

	@Generated
	@Selector("setTimeout:")
	public native void setTimeout(double value);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("start")
	public native void start();

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("timeout")
	public native double timeout();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}
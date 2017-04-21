package de.kromzem.bindings.bolts;


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
import apple.foundation.NSException;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;

@Generated
@Library("Bolts")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class BFTask<_ResultType> extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected BFTask(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native BFTask<?> alloc();

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
	@Selector("cancelledTask")
	@MappedReturn(ObjCObjectMapper.class)
	public static native <_ResultType> Object cancelledTask();

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
	@Selector("continueWithBlock:")
	public native BFTask<?> continueWithBlock(
			@ObjCBlock(name = "call_continueWithBlock") Block_continueWithBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_continueWithBlock {
		@Generated
		@MappedReturn(ObjCObjectMapper.class)
		Object call_continueWithBlock(BFTask<Object> arg0);
	}

	@Generated
	@Selector("continueWithBlock:cancellationToken:")
	public native BFTask<?> continueWithBlockCancellationToken(
			@ObjCBlock(name = "call_continueWithBlockCancellationToken") Block_continueWithBlockCancellationToken block,
			BFCancellationToken cancellationToken);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_continueWithBlockCancellationToken {
		@Generated
		@MappedReturn(ObjCObjectMapper.class)
		Object call_continueWithBlockCancellationToken(BFTask<Object> arg0);
	}

	@Generated
	@Selector("continueWithExecutor:block:cancellationToken:")
	public native BFTask<?> continueWithExecutorBlockCancellationToken(
			BFExecutor executor,
			@ObjCBlock(name = "call_continueWithExecutorBlockCancellationToken") Block_continueWithExecutorBlockCancellationToken block,
			BFCancellationToken cancellationToken);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_continueWithExecutorBlockCancellationToken {
		@Generated
		@MappedReturn(ObjCObjectMapper.class)
		Object call_continueWithExecutorBlockCancellationToken(
				BFTask<Object> arg0);
	}

	@Generated
	@Selector("continueWithExecutor:successBlock:cancellationToken:")
	public native BFTask<?> continueWithExecutorSuccessBlockCancellationToken(
			BFExecutor executor,
			@ObjCBlock(name = "call_continueWithExecutorSuccessBlockCancellationToken") Block_continueWithExecutorSuccessBlockCancellationToken block,
			BFCancellationToken cancellationToken);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_continueWithExecutorSuccessBlockCancellationToken {
		@Generated
		@MappedReturn(ObjCObjectMapper.class)
		Object call_continueWithExecutorSuccessBlockCancellationToken(
				BFTask<Object> arg0);
	}

	@Generated
	@Selector("continueWithExecutor:withBlock:")
	public native BFTask<?> continueWithExecutorWithBlock(
			BFExecutor executor,
			@ObjCBlock(name = "call_continueWithExecutorWithBlock") Block_continueWithExecutorWithBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_continueWithExecutorWithBlock {
		@Generated
		@MappedReturn(ObjCObjectMapper.class)
		Object call_continueWithExecutorWithBlock(BFTask<Object> arg0);
	}

	@Generated
	@Selector("continueWithExecutor:withSuccessBlock:")
	public native BFTask<?> continueWithExecutorWithSuccessBlock(
			BFExecutor executor,
			@ObjCBlock(name = "call_continueWithExecutorWithSuccessBlock") Block_continueWithExecutorWithSuccessBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_continueWithExecutorWithSuccessBlock {
		@Generated
		@MappedReturn(ObjCObjectMapper.class)
		Object call_continueWithExecutorWithSuccessBlock(BFTask<Object> arg0);
	}

	@Generated
	@Selector("continueWithSuccessBlock:")
	public native BFTask<?> continueWithSuccessBlock(
			@ObjCBlock(name = "call_continueWithSuccessBlock") Block_continueWithSuccessBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_continueWithSuccessBlock {
		@Generated
		@MappedReturn(ObjCObjectMapper.class)
		Object call_continueWithSuccessBlock(BFTask<Object> arg0);
	}

	@Generated
	@Selector("continueWithSuccessBlock:cancellationToken:")
	public native BFTask<?> continueWithSuccessBlockCancellationToken(
			@ObjCBlock(name = "call_continueWithSuccessBlockCancellationToken") Block_continueWithSuccessBlockCancellationToken block,
			BFCancellationToken cancellationToken);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_continueWithSuccessBlockCancellationToken {
		@Generated
		@MappedReturn(ObjCObjectMapper.class)
		Object call_continueWithSuccessBlockCancellationToken(
				BFTask<Object> arg0);
	}

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("error")
	public native NSError error();

	@Generated
	@Deprecated
	@Selector("exception")
	public native NSException exception();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native BFTask<?> init();

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
	@Selector("isCancelled")
	public native boolean isCancelled();

	@Generated
	@Selector("isCompleted")
	public native boolean isCompleted();

	@Generated
	@Selector("isFaulted")
	public native boolean isFaulted();

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
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("result")
	@MappedReturn(ObjCObjectMapper.class)
	public native _ResultType result();

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("taskForCompletionOfAllTasks:")
	public static native <_ResultType> BFTask<?> taskForCompletionOfAllTasks(
			NSArray<? extends BFTask<?>> tasks);

	@Generated
	@Selector("taskForCompletionOfAllTasksWithResults:")
	public static native <_ResultType> BFTask<?> taskForCompletionOfAllTasksWithResults(
			NSArray<? extends BFTask<?>> tasks);

	@Generated
	@Selector("taskForCompletionOfAnyTask:")
	public static native <_ResultType> BFTask<?> taskForCompletionOfAnyTask(
			NSArray<? extends BFTask<?>> tasks);

	@Generated
	@Selector("taskFromExecutor:withBlock:")
	public static native <_ResultType> BFTask<?> taskFromExecutorWithBlock(
			BFExecutor executor,
			@ObjCBlock(name = "call_taskFromExecutorWithBlock") Block_taskFromExecutorWithBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_taskFromExecutorWithBlock {
		@Generated
		@MappedReturn(ObjCObjectMapper.class)
		Object call_taskFromExecutorWithBlock();
	}

	@Generated
	@Selector("taskWithDelay:")
	public static native <_ResultType> BFTask<?> taskWithDelay(int millis);

	@Generated
	@Selector("taskWithDelay:cancellationToken:")
	public static native <_ResultType> BFTask<?> taskWithDelayCancellationToken(
			int millis, BFCancellationToken token);

	@Generated
	@Selector("taskWithError:")
	public static native <_ResultType> BFTask<?> taskWithError(NSError error);

	@Generated
	@Deprecated
	@Selector("taskWithException:")
	public static native <_ResultType> BFTask<?> taskWithException(
			NSException exception);

	@Generated
	@Selector("taskWithResult:")
	public static native <_ResultType> BFTask<?> taskWithResult(
			@Mapped(ObjCObjectMapper.class) _ResultType result);

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();

	@Generated
	@Selector("waitUntilFinished")
	public native void waitUntilFinished();
}
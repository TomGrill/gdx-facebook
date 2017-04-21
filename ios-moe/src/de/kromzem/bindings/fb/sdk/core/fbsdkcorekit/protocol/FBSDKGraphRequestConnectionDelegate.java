package de.kromzem.bindings.fb.sdk.core.fbsdkcorekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.NInt;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IsOptional;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSError;
import de.kromzem.bindings.fb.sdk.core.fbsdkcorekit.FBSDKGraphRequestConnection;

@Generated
@Library("FBSDKCoreKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKGraphRequestConnectionDelegate")
public interface FBSDKGraphRequestConnectionDelegate {
	@Generated
	@IsOptional
	@Selector("requestConnection:didFailWithError:")
	default void requestConnectionDidFailWithError(
			FBSDKGraphRequestConnection connection, NSError error) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("requestConnection:didSendBodyData:totalBytesWritten:totalBytesExpectedToWrite:")
	default void requestConnectionDidSendBodyDataTotalBytesWrittenTotalBytesExpectedToWrite(
			FBSDKGraphRequestConnection connection, @NInt long bytesWritten,
			@NInt long totalBytesWritten, @NInt long totalBytesExpectedToWrite) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("requestConnectionDidFinishLoading:")
	default void requestConnectionDidFinishLoading(
			FBSDKGraphRequestConnection connection) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("requestConnectionWillBeginLoading:")
	default void requestConnectionWillBeginLoading(
			FBSDKGraphRequestConnection connection) {
		throw new java.lang.UnsupportedOperationException();
	}
}
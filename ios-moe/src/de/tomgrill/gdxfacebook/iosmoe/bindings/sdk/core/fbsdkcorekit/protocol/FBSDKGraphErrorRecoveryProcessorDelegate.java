package de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.core.fbsdkcorekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IsOptional;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSError;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.core.fbsdkcorekit.FBSDKGraphErrorRecoveryProcessor;

@Generated
@Library("FBSDKCoreKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKGraphErrorRecoveryProcessorDelegate")
public interface FBSDKGraphErrorRecoveryProcessorDelegate {
	@Generated
	@Selector("processorDidAttemptRecovery:didRecover:error:")
	void processorDidAttemptRecoveryDidRecoverError(
            FBSDKGraphErrorRecoveryProcessor processor, boolean didRecover,
            NSError error);

	@Generated
	@IsOptional
	@Selector("processorWillProcessError:error:")
	default boolean processorWillProcessErrorError(
			FBSDKGraphErrorRecoveryProcessor processor, NSError error) {
		throw new java.lang.UnsupportedOperationException();
	}
}
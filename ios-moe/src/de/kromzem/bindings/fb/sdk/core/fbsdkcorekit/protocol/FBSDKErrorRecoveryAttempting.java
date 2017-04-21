package de.kromzem.bindings.fb.sdk.core.fbsdkcorekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.NUInt;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.VoidPtr;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.SEL;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import apple.foundation.NSError;

@Generated
@Library("FBSDKCoreKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKErrorRecoveryAttempting")
public interface FBSDKErrorRecoveryAttempting {
	@Generated
	@Selector("attemptRecoveryFromError:optionIndex:delegate:didRecoverSelector:contextInfo:")
	void attemptRecoveryFromErrorOptionIndexDelegateDidRecoverSelectorContextInfo(
			NSError error, @NUInt long recoveryOptionIndex,
			@Mapped(ObjCObjectMapper.class) Object delegate,
			SEL didRecoverSelector, VoidPtr contextInfo);
}
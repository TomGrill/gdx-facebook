package de.kromzem.bindings.fb.sdk.share.fbsdksharekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import de.kromzem.bindings.fb.sdk.share.fbsdksharekit.FBSDKGameRequestDialog;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKGameRequestDialogDelegate")
public interface FBSDKGameRequestDialogDelegate {
	@Generated
	@Selector("gameRequestDialog:didCompleteWithResults:")
	void gameRequestDialogDidCompleteWithResults(
			FBSDKGameRequestDialog gameRequestDialog, NSDictionary<?, ?> results);

	@Generated
	@Selector("gameRequestDialog:didFailWithError:")
	void gameRequestDialogDidFailWithError(
			FBSDKGameRequestDialog gameRequestDialog, NSError error);

	@Generated
	@Selector("gameRequestDialogDidCancel:")
	void gameRequestDialogDidCancel(FBSDKGameRequestDialog gameRequestDialog);
}
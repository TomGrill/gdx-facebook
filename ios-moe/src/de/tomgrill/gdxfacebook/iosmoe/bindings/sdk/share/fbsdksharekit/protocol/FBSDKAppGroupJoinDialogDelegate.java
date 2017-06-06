package de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.FBSDKAppGroupJoinDialog;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKAppGroupJoinDialogDelegate")
public interface FBSDKAppGroupJoinDialogDelegate {
	@Generated
	@Deprecated
	@Selector("appGroupJoinDialog:didCompleteWithResults:")
	void appGroupJoinDialogDidCompleteWithResults(
			FBSDKAppGroupJoinDialog appGroupJoinDialog,
			NSDictionary<?, ?> results);

	@Generated
	@Deprecated
	@Selector("appGroupJoinDialog:didFailWithError:")
	void appGroupJoinDialogDidFailWithError(
			FBSDKAppGroupJoinDialog appGroupJoinDialog, NSError error);

	@Generated
	@Deprecated
	@Selector("appGroupJoinDialogDidCancel:")
	void appGroupJoinDialogDidCancel(FBSDKAppGroupJoinDialog appGroupJoinDialog);
}
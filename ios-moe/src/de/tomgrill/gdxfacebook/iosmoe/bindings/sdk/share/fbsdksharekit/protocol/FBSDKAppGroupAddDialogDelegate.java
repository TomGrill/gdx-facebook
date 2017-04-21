package de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.FBSDKAppGroupAddDialog;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKAppGroupAddDialogDelegate")
public interface FBSDKAppGroupAddDialogDelegate {
	@Generated
	@Deprecated
	@Selector("appGroupAddDialog:didCompleteWithResults:")
	void appGroupAddDialogDidCompleteWithResults(
			FBSDKAppGroupAddDialog appGroupAddDialog, NSDictionary<?, ?> results);

	@Generated
	@Deprecated
	@Selector("appGroupAddDialog:didFailWithError:")
	void appGroupAddDialogDidFailWithError(
			FBSDKAppGroupAddDialog appGroupAddDialog, NSError error);

	@Generated
	@Deprecated
	@Selector("appGroupAddDialogDidCancel:")
	void appGroupAddDialogDidCancel(FBSDKAppGroupAddDialog appGroupAddDialog);
}
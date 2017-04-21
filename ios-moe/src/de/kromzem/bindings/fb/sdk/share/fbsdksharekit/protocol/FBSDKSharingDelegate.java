package de.kromzem.bindings.fb.sdk.share.fbsdksharekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import apple.foundation.NSDictionary;
import apple.foundation.NSError;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKSharingDelegate")
public interface FBSDKSharingDelegate {
	@Generated
	@Selector("sharer:didCompleteWithResults:")
	void sharerDidCompleteWithResults(
			@Mapped(ObjCObjectMapper.class) Object sharer,
			NSDictionary<?, ?> results);

	@Generated
	@Selector("sharer:didFailWithError:")
	void sharerDidFailWithError(@Mapped(ObjCObjectMapper.class) Object sharer,
			NSError error);

	@Generated
	@Selector("sharerDidCancel:")
	void sharerDidCancel(@Mapped(ObjCObjectMapper.class) Object sharer);
}
package de.kromzem.bindings.fb.sdk.share.fbsdksharekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKSharingButton")
public interface FBSDKSharingButton {
	@Generated
	@Selector("setShareContent:")
	void setShareContent(@Mapped(ObjCObjectMapper.class) Object value);

	@Generated
	@Selector("shareContent")
	@MappedReturn(ObjCObjectMapper.class)
	Object shareContent();
}
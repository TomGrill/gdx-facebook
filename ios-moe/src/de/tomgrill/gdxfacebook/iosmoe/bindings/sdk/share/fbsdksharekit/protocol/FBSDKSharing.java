package de.tomgrill.gdxfacebook.iosmoe.bindings.sdk.share.fbsdksharekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.ReferenceInfo;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.Ptr;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import apple.foundation.NSError;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKSharing")
public interface FBSDKSharing {
	@Generated
	@Selector("delegate")
	@MappedReturn(ObjCObjectMapper.class)
	Object delegate();

	@Generated
	@Selector("setDelegate:")
	void setDelegate(@Mapped(ObjCObjectMapper.class) Object value);

	@Generated
	@Selector("setShareContent:")
	void setShareContent(@Mapped(ObjCObjectMapper.class) Object value);

	@Generated
	@Selector("setShouldFailOnDataError:")
	void setShouldFailOnDataError(boolean value);

	@Generated
	@Selector("shareContent")
	@MappedReturn(ObjCObjectMapper.class)
	Object shareContent();

	@Generated
	@Selector("shouldFailOnDataError")
	boolean shouldFailOnDataError();

	@Generated
	@Selector("validateWithError:")
	boolean validateWithError(
			@ReferenceInfo(type = NSError.class) Ptr<NSError> errorRef);
}
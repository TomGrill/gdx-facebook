package de.kromzem.bindings.fb.sdk.share.fbsdksharekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.NUInt;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKLiking")
public interface FBSDKLiking {
	@Generated
	@Selector("objectID")
	String objectID();

	@Generated
	@Selector("objectType")
	@NUInt
	long objectType();

	@Generated
	@Selector("setObjectID:")
	void setObjectID(String value);

	@Generated
	@Selector("setObjectType:")
	void setObjectType(@NUInt long value);
}
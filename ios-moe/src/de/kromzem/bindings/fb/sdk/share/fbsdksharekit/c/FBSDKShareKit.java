package de.kromzem.bindings.fb.sdk.share.fbsdksharekit.c;


import org.moe.natj.c.CRuntime;
import org.moe.natj.c.ann.CFunction;
import org.moe.natj.c.ann.CVariable;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.NUInt;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.map.ObjCStringMapper;

@Generated
@Library("FBSDKShareKit")
@Runtime(CRuntime.class)
public final class FBSDKShareKit {
	static {
		NatJ.register();
	}

	@Generated
	private FBSDKShareKit() {
	}

	@Generated
	@CFunction
	@MappedReturn(ObjCStringMapper.class)
	public static native String NSStringFromFBSDKAppGroupPrivacy(
			@NUInt long privacy);

	@Generated
	@CFunction
	@MappedReturn(ObjCStringMapper.class)
	public static native String NSStringFromFBSDKLikeObjectType(
			@NUInt long objectType);

	@Generated
	@CFunction
	@MappedReturn(ObjCStringMapper.class)
	public static native String NSStringFromFBSDKLikeControlAuxiliaryPosition(
			@NUInt long auxiliaryPosition);

	@Generated
	@CFunction
	@MappedReturn(ObjCStringMapper.class)
	public static native String NSStringFromFBSDKLikeControlHorizontalAlignment(
			@NUInt long horizontalAlignment);

	@Generated
	@CFunction
	@MappedReturn(ObjCStringMapper.class)
	public static native String NSStringFromFBSDKLikeControlStyle(
			@NUInt long style);

	@Generated
	@CFunction
	@MappedReturn(ObjCStringMapper.class)
	public static native String NSStringFromFBSDKShareDialogMode(
			@NUInt long dialogMode);

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FBSDKShareErrorDomain();
}
package de.kromzem.bindings.bolts.c;


import org.moe.natj.c.CRuntime;
import org.moe.natj.c.ann.CFunction;
import org.moe.natj.c.ann.CVariable;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.NInt;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.map.ObjCStringMapper;

@Generated
@Library("Bolts")
@Runtime(CRuntime.class)
public final class Bolts {
	static {
		NatJ.register();
	}

	@Generated
	private Bolts() {
	}

	@Generated
	@Deprecated
	@CFunction
	public static native boolean BFTaskCatchesExceptions();

	@Generated
	@Deprecated
	@CFunction
	public static native void BFTaskSetCatchesExceptions(boolean catchExceptions);

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFTaskErrorDomain();

	@Generated
	@CVariable()
	@NInt
	public static native long kBFMultipleErrorsError();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFTaskMultipleExceptionsException();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFTaskMultipleErrorsUserInfoKey();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFTaskMultipleExceptionsUserInfoKey();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFAppLinkVersion();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFMeasurementEventNotificationName();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFMeasurementEventNameKey();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFMeasurementEventArgsKey();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFAppLinkParseEventName();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFAppLinkNavigateInEventName();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFAppLinkNavigateOutEventName();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BFAppLinkNavigateBackToReferrerEventName();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String BoltsFrameworkVersionString();
}
package de.kromzem.bindings.bolts.protocol;

import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSURL;
import de.kromzem.bindings.bolts.BFTask;

@Generated
@Library("Bolts")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("BFAppLinkResolving")
public interface BFAppLinkResolving {
	@Generated
	@Selector("appLinkFromURLInBackground:")
	BFTask<?> appLinkFromURLInBackground(NSURL url);
}
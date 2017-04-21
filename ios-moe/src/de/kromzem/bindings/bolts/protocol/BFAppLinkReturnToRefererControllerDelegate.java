package de.kromzem.bindings.bolts.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.NInt;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IsOptional;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import de.kromzem.bindings.bolts.BFAppLink;
import de.kromzem.bindings.bolts.BFAppLinkReturnToRefererController;

@Generated
@Library("Bolts")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("BFAppLinkReturnToRefererControllerDelegate")
public interface BFAppLinkReturnToRefererControllerDelegate {
	@Generated
	@IsOptional
	@Selector("returnToRefererController:didNavigateToAppLink:type:")
	default void returnToRefererControllerDidNavigateToAppLinkType(
			BFAppLinkReturnToRefererController controller, BFAppLink url,
			@NInt long type) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("returnToRefererController:willNavigateToAppLink:")
	default void returnToRefererControllerWillNavigateToAppLink(
			BFAppLinkReturnToRefererController controller, BFAppLink appLink) {
		throw new java.lang.UnsupportedOperationException();
	}
}
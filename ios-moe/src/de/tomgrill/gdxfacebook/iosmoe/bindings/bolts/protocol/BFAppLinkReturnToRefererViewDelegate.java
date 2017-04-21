package de.tomgrill.gdxfacebook.iosmoe.bindings.bolts.protocol;


import de.tomgrill.gdxfacebook.iosmoe.bindings.bolts.BFAppLinkReturnToRefererView;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import de.tomgrill.gdxfacebook.iosmoe.bindings.bolts.BFAppLink;

@Generated
@Library("Bolts")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("BFAppLinkReturnToRefererViewDelegate")
public interface BFAppLinkReturnToRefererViewDelegate {
	@Generated
	@Selector("returnToRefererViewDidTapInsideCloseButton:")
	void returnToRefererViewDidTapInsideCloseButton(
			BFAppLinkReturnToRefererView view);

	@Generated
	@Selector("returnToRefererViewDidTapInsideLink:link:")
	void returnToRefererViewDidTapInsideLinkLink(
			BFAppLinkReturnToRefererView view, BFAppLink link);
}
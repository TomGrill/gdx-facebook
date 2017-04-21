package de.kromzem.bindings.fb.sdk.share.fbsdksharekit.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.BoolPtr;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCBlock;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

import apple.foundation.NSArray;
import apple.foundation.NSDictionary;
import apple.foundation.NSEnumerator;
import apple.foundation.NSNumber;
import apple.foundation.NSURL;
import apple.foundation.protocol.NSSecureCoding;
import de.kromzem.bindings.fb.sdk.share.fbsdksharekit.FBSDKShareOpenGraphObject;
import de.kromzem.bindings.fb.sdk.share.fbsdksharekit.FBSDKSharePhoto;

@Generated
@Library("FBSDKShareKit")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FBSDKShareOpenGraphValueContaining")
public interface FBSDKShareOpenGraphValueContaining extends NSSecureCoding {
	@Generated
	@Selector("URLForKey:")
	NSURL URLForKey(String key);

	@Generated
	@Selector("arrayForKey:")
	NSArray<?> arrayForKey(String key);

	@Generated
	@Selector("enumerateKeysAndObjectsUsingBlock:")
	void enumerateKeysAndObjectsUsingBlock(
			@ObjCBlock(name = "call_enumerateKeysAndObjectsUsingBlock") Block_enumerateKeysAndObjectsUsingBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_enumerateKeysAndObjectsUsingBlock {
		@Generated
		void call_enumerateKeysAndObjectsUsingBlock(String arg0,
				@Mapped(ObjCObjectMapper.class) Object arg1, BoolPtr arg2);
	}

	@Generated
	@Selector("keyEnumerator")
	NSEnumerator<?> keyEnumerator();

	@Generated
	@Selector("numberForKey:")
	NSNumber numberForKey(String key);

	@Generated
	@Selector("objectEnumerator")
	NSEnumerator<?> objectEnumerator();

	@Generated
	@Selector("objectForKey:")
	FBSDKShareOpenGraphObject objectForKey(String key);

	@Generated
	@Selector("objectForKeyedSubscript:")
	@MappedReturn(ObjCObjectMapper.class)
	Object objectForKeyedSubscript(String key);

	@Generated
	@Selector("parseProperties:")
	void parseProperties(NSDictionary<?, ?> properties);

	@Generated
	@Selector("photoForKey:")
	FBSDKSharePhoto photoForKey(String key);

	@Generated
	@Selector("removeObjectForKey:")
	void removeObjectForKey(String key);

	@Generated
	@Selector("setArray:forKey:")
	void setArrayForKey(NSArray<?> array, String key);

	@Generated
	@Selector("setNumber:forKey:")
	void setNumberForKey(NSNumber number, String key);

	@Generated
	@Selector("setObject:forKey:")
	void setObjectForKey(FBSDKShareOpenGraphObject object, String key);

	@Generated
	@Selector("setPhoto:forKey:")
	void setPhotoForKey(FBSDKSharePhoto photo, String key);

	@Generated
	@Selector("setString:forKey:")
	void setStringForKey(String string, String key);

	@Generated
	@Selector("setURL:forKey:")
	void setURLForKey(NSURL URL, String key);

	@Generated
	@Selector("stringForKey:")
	String stringForKey(String key);
}
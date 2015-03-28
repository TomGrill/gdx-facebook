package de.tpronold.gdxfacebook.core.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.tpronold.gdxfacebook.core.FacebookUtils;

public class FacebookUtilsUnitTests {

	@Test
	public void permissionSplit() {
		String s = "s1 ,  s2,s3,  s4   ";

		assertEquals("s1", FacebookUtils.permissionSplit(s)[0]);
		assertEquals("s2", FacebookUtils.permissionSplit(s)[1]);
		assertEquals("s3", FacebookUtils.permissionSplit(s)[2]);
		assertEquals("s4", FacebookUtils.permissionSplit(s)[3]);
	}
}

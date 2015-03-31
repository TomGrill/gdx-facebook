/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tomgrill.gdxfacebook.core.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import de.tomgrill.gdxfacebook.core.FacebookUtils;

public class FacebookUtilsUnitTests {

	@Test
	public void permissionSplit() {
		String s = "s1 ,  s2,s3,  s4   ";

		assertEquals("s1", FacebookUtils.permissionSplit(s)[0]);
		assertEquals("s2", FacebookUtils.permissionSplit(s)[1]);
		assertEquals("s3", FacebookUtils.permissionSplit(s)[2]);
		assertEquals("s4", FacebookUtils.permissionSplit(s)[3]);
	}

	@Test
	public void permissionSplitToList() {
		String s = "s1 ,  s2,s3,  s4   ";
		List<String> list = (List<String>) FacebookUtils.permissionSplitToList(s);
		assertEquals("s1", list.get(0));
		assertEquals("s2", list.get(1));
		assertEquals("s3", list.get(2));
		assertEquals("s4", list.get(3));
	}
}

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

package de.tomgrill.gdxfacebook.core;

import java.util.ArrayList;
import java.util.List;

public class FacebookUtils {
	public static String[] permissionSplit(String stringToSplit) {
		String[] result = stringToSplit.split(",");

		for (int i = 0; i < result.length; i++) {
			result[i] = result[i].trim();
		}

		return result;

	}

	public static List<String> permissionSplitToList(String stringToSplit) {

		String[] tmpResult = permissionSplit(stringToSplit);

		List<String> result = new ArrayList<String>();

		for (int i = 0; i < tmpResult.length; i++) {
			result.add(tmpResult[i]);
		}

		return result;

	}
}

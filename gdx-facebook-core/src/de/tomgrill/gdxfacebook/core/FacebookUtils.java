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

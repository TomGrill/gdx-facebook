package de.tpronold.gdxfacebook.core;

public class FacebookUtils {
	public static String[] permissionSplit(String stringToSplit) {
		String[] result = stringToSplit.split(",");

		for (int i = 0; i < result.length; i++) {
			result[i] = result[i].trim();
		}

		return result;

	}
}

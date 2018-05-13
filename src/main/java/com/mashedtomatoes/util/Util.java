package com.mashedtomatoes.util;

public class Util {
	private static final String UnknownImageUrl = "/img/unknown.png"	;

	public static String resolveFilesUrl(String filesUri, String resourceUri) {
		if (resourceUri == null) {
			return UnknownImageUrl;
		}

		if (resourceUri.toLowerCase().startsWith(filesUri.toLowerCase())) {
			return resourceUri;
		}

		return String.format("%s%s", filesUri, resourceUri);
	}

	public static Integer clamp(Integer value, Integer lower, Integer upper) {
		return Math.max(lower, Math.min(value, upper));
	}
}

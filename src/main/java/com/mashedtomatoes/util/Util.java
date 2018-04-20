package com.mashedtomatoes.util;

public class Util {
	public static String resolveFilesUrl(String filesUri, String resourceUri) {
		if (resourceUri == null) {
			resourceUri = "/img/unknown.png";
		}
		if (resourceUri.toLowerCase().startsWith(filesUri.toLowerCase())) {
			return resourceUri;
		}

		return String.format("%s%s", filesUri, resourceUri);
	}
}

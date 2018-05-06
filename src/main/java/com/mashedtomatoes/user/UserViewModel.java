package com.mashedtomatoes.user;

import com.mashedtomatoes.util.Util;

public class UserViewModel extends User {
	public UserViewModel(User base, String fileUri) {
		super.setId(base.getId());
		super.setCredentials(base.getCredentials());
		super.setVerification(base.getVerification());
		super.setType(base.getType());
		super.setRatings(base.getRatings());
		getRatings().stream().forEach(rating -> {
			final String path = rating.getMedia().getPosterPath();
			rating.getMedia().setPosterPath(Util.resolveFilesUrl(fileUri,path));
		});

		super.setFollowing(base.getFollowing());
		super.setFollowers(base.getFollowers());
		super.setWantToSee(base.getWantToSee());
		base.getWantToSee().stream().forEach(media -> {
			media.setPosterPath(Util.resolveFilesUrl(fileUri, media.getPosterPath()));
		});
		super.setNotInterested(base.getNotInterested());
		base.getNotInterested().stream().forEach(media -> {
			media.setPosterPath(Util.resolveFilesUrl(fileUri, media.getPosterPath()));
		});
		super.setProfileViews(base.getProfileViews());
		super.setCreated(base.getCreated());
		super.setUpdated(base.getUpdated());
	}
}

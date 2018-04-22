package com.mashedtomatoes.user;

public class UserViewModel extends User {
	public UserViewModel(User base) {
		super.setId(base.getId());
		super.setCredentials(base.getCredentials());
		super.setVerification(base.getVerification());
		super.setType(base.getType());
		super.setBirthDate(base.getBirthDate());
		super.setRatings(base.getRatings());
		super.setFollowing(base.getFollowing());
		super.setFollowers(base.getFollowers());
		super.setWantToSee(base.getWantToSee());
		super.setNotInterested(base.getNotInterested());
		super.setProfileViews(base.getProfileViews());
		super.setCreated(base.getCreated());
		super.setUpdated(base.getUpdated());
	}
}

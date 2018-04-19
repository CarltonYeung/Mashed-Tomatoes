package com.mashedtomatoes.celebrity;

public class CelebrityViewModel extends Celebrity {
	public CelebrityViewModel(String filesPath, Celebrity base) {
		super.setId(base.getId());
		super.setBirthday(base.getBirthday());
		super.setBirthplace(base.getBirthplace());
		super.setBiography(base.getBiography());
		super.setProfilePath(String.format("%s%s", filesPath, base.getProfilePath()));
	}
}

package com.mashedtomatoes.user;

public enum AudienceRank {
	BRONZE("Bronze"),
	SILVER("Silver"),
	GOLD("Gold"),
	PLATINUM("Platinum"),
	DIAMOND("Diamond");

	private String name;

	AudienceRank(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}

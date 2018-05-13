<<<<<<< HEAD
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
=======
package com.mashedtomatoes.user;

public enum AudienceRank {
	BRONZE("Bronze"),
	SILVER("Silver"),
	GOLD("Gold");

	private String name;

	AudienceRank(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
>>>>>>> ba6e3eebfaacf72977facf025edb2f576b51a94d

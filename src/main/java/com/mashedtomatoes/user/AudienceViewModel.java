<<<<<<< HEAD
package com.mashedtomatoes.user;

public class AudienceViewModel extends UserViewModel {
  private final String displayName;
  private final boolean publicProfile;
  private final AudienceRank rank;

  public AudienceViewModel(Audience base, String fileUri, Integer rankUpgradeRate) {
    super(base, fileUri);
    displayName = base.getDisplayName();
    publicProfile = base.isPublicProfile();
    Integer followerCount = getFollowers().size();
    if (followerCount < rankUpgradeRate) {
      rank = AudienceRank.BRONZE;
    } else if (followerCount < rankUpgradeRate * 2) {
      rank = AudienceRank.SILVER;
    } else if (followerCount < rankUpgradeRate * 3) {
      rank = AudienceRank.GOLD;
    } else if (followerCount < rankUpgradeRate * 4) {
      rank = AudienceRank.PLATINUM;
    } else {
      rank = AudienceRank.DIAMOND;
    }
  }

  public String getDisplayName() {
    return displayName;
  }

  public boolean isPublicProfile() {
    return publicProfile;
  }

  public AudienceRank getRank() {
    return rank;
  }
}
=======
package com.mashedtomatoes.user;

public class AudienceViewModel extends UserViewModel {
  private final String displayName;
  private final boolean publicProfile;
  private final AudienceRank rank;

  public AudienceViewModel(Audience base, String fileUri, Integer rankUpgradeRate) {
    super(base, fileUri);
    displayName = base.getDisplayName();
    publicProfile = base.isPublicProfile();
    Integer followerCount = getFollowers().size();
    if (followerCount < rankUpgradeRate) {
      rank = AudienceRank.BRONZE;
    } else if (followerCount < rankUpgradeRate * 2) {
      rank = AudienceRank.SILVER;
    } else {
      rank = AudienceRank.GOLD;
    }
  }

  public String getDisplayName() {
    return displayName;
  }

  public boolean isPublicProfile() {
    return publicProfile;
  }

  public AudienceRank getRank() {
    return rank;
  }

  public boolean isBronze() {
    return getRank().equals(AudienceRank.BRONZE);
  }

  public boolean isSilver() {
    return getRank().equals(AudienceRank.SILVER);
  }

  public boolean isGold() {
    return getRank().equals(AudienceRank.GOLD);
  }
}
>>>>>>> ba6e3eebfaacf72977facf025edb2f576b51a94d

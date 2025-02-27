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

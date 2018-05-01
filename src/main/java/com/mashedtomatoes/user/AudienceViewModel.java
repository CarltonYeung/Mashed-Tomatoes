package com.mashedtomatoes.user;

public class AudienceViewModel extends UserViewModel {
  private final String displayName;
  private final Favorite favorites;
  private final boolean publicProfile;
  private final AudienceRank rank;

  public AudienceViewModel(Audience base, Integer rankUpgradeRate) {
    super(base);
    displayName = base.getDisplayName();
    favorites = base.getFavorites();
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

  public Favorite getFavorites() {
    return favorites;
  }

  public boolean isPublicProfile() {
    return publicProfile;
  }

  public AudienceRank getRank() {
    return rank;
  }
}

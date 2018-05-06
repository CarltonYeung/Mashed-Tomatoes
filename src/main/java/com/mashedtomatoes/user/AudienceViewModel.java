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

package com.mashedtomatoes.http;

import javax.validation.constraints.NotNull;

public class ChangePrivacyRequest {
  private boolean publicProfile;

  public ChangePrivacyRequest() {
  }

  @NotNull
  public boolean isPublicProfile() {
    return publicProfile;
  }

  public void setPublicProfile(boolean publicProfile) {
    this.publicProfile = publicProfile;
  }
}

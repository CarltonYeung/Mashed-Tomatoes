package com.mashedtomatoes.http;

import javax.validation.constraints.NotNull;

public class CriticApplicationStatusRequest {
  private boolean approved;

  public CriticApplicationStatusRequest() {
  }

  @NotNull
  public boolean isApproved() {
    return approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }
}

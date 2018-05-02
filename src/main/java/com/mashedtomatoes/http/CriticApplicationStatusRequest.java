package com.mashedtomatoes.http;

import javax.validation.constraints.NotNull;

public class CriticApplicationStatusRequest {
  private boolean accepted;

  public CriticApplicationStatusRequest() {
  }

  @NotNull
  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }
}

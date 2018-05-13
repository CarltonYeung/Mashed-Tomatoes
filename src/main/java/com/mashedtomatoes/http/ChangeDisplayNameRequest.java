package com.mashedtomatoes.http;

import javax.validation.constraints.NotNull;

public class ChangeDisplayNameRequest {
  private String displayName;

  public ChangeDisplayNameRequest() {
  }

  @NotNull
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}

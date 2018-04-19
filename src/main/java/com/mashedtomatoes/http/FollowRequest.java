package com.mashedtomatoes.http;

import javax.validation.constraints.NotNull;

public class FollowRequest {
  private long id; // id of user to follow

  public FollowRequest() {}

  @NotNull
  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}

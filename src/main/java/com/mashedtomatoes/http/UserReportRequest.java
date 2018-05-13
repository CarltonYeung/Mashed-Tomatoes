package com.mashedtomatoes.http;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserReportRequest {
  private long userId;
  private String reason;

  public UserReportRequest() {
  }

  @NotNull
  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  @NotEmpty
  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}

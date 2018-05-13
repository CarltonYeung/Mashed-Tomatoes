package com.mashedtomatoes.http;

import javax.validation.constraints.NotEmpty;

public class ChangePasswordRequest {
  private String newPlaintextPassword;
  private String oldPlaintextPassword;

  public ChangePasswordRequest() {}

  @NotEmpty
  public String getNewPlaintextPassword() {
    return newPlaintextPassword;
  }

  public void setNewPlaintextPassword(String newPlaintextPassword) {
    this.newPlaintextPassword = newPlaintextPassword;
  }

  @NotEmpty
  public String getOldPlaintextPassword() {
    return oldPlaintextPassword;
  }

  public void setOldPlaintextPassword(String oldPlaintextPassword) {
    this.oldPlaintextPassword = oldPlaintextPassword;
  }
}

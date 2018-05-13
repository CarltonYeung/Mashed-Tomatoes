package com.mashedtomatoes.http;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ChangeEmailRequest {
  private String newEmail;
  private String password;

  public ChangeEmailRequest() {}

  @NotEmpty
  @Email
  public String getNewEmail() {
    return newEmail;
  }

  public void setNewEmail(String newEmail) {
    this.newEmail = newEmail;
  }

  @NotEmpty
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

package com.mashedtomatoes.http;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class RegisterRequest {
  private String email;
  private String password;
  private String displayName;
  private boolean wantToBeAdmin;
  private String recaptchaResponse;

  public RegisterRequest() {}

  @NotEmpty
  @Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @NotEmpty
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @NotEmpty
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public boolean isWantToBeAdmin() {
    return wantToBeAdmin;
  }

  public void setWantToBeAdmin(boolean wantToBeAdmin) {
    this.wantToBeAdmin = wantToBeAdmin;
  }

  @NotEmpty
  public String getRecaptchaResponse() {
    return recaptchaResponse;
  }

  public void setRecaptchaResponse(String recaptchaResponse) {
    this.recaptchaResponse = recaptchaResponse;
  }
}

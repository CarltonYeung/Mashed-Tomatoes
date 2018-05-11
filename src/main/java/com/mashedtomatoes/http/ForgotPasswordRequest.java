package com.mashedtomatoes.http;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ForgotPasswordRequest {
  private String email;

  public ForgotPasswordRequest() {
  }

  @NotEmpty
  @Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

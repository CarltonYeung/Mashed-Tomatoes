package com.mashedtomatoes.http;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ResendVerificationEmailRequest {
  private String email;

  public ResendVerificationEmailRequest() {}

  @NotEmpty
  @Email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

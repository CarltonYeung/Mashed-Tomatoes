package com.mashedtomatoes.http;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginRequest {
  private String email;
  private String password;

  public LoginRequest() {}

  @NotEmpty
  @Size(max = 255)
  @Email
  public String getEmail() {
    return email;
  }

  @NotEmpty
  public String getPassword() {
    return password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

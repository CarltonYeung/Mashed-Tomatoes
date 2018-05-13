package com.mashedtomatoes.http;

import javax.validation.constraints.NotEmpty;

public class CriticApplicationRequest {
  private String firstName;
  private String lastName;
  private String body;

  public CriticApplicationRequest() {
  }

  @NotEmpty
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @NotEmpty
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @NotEmpty
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}

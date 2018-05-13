package com.mashedtomatoes.user;

public class CriticViewModel extends UserViewModel {
  private final String firstName;
  private final String lastName;
  private final String fullName;
  private final boolean topCritic;

  public CriticViewModel(Critic base, String fileUri) {
    super(base, fileUri);
    firstName = base.getFirstName();
    lastName = base.getFirstName();
    fullName = String.format("%s %s", firstName, lastName);
    topCritic = base.isTopCritic();
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return fullName;
  }

  public boolean isTopCritic() {
    return topCritic;
  }
}

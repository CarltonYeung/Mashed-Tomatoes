package com.mashedtomatoes.user;

import com.mashedtomatoes.rating.Publisher;

public class CriticViewModel extends UserViewModel {
  private final String firstName;
  private final String lastName;
  private final String fullName;
  private final Publisher publisher;
  private final boolean topCritic;

  public CriticViewModel(Critic base) {
    super(base);
    firstName = base.getFirstName();
    lastName = base.getFirstName();
    fullName = String.format("%s %s", firstName, lastName);
    publisher = base.getPublisher();
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

  public Publisher getPublisher() {
    return publisher;
  }

  public boolean isTopCritic() {
    return topCritic;
  }
}

package com.mashedtomatoes.user;

import com.mashedtomatoes.rating.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CriticViewModel extends UserViewModel {

  private final String firstName;
  private final String lastName;
  private final String fullName;
  private final boolean topCritic;
  private List<Rating> bestReviewed;
  private List<Rating> worstReviewed;

  public CriticViewModel(Critic base, String fileUri) {
    super(base, fileUri);
    firstName = base.getFirstName();
    lastName = base.getFirstName();
    fullName = String.format("%s %s", firstName, lastName);
    topCritic = base.isTopCritic();
    bestReviewed = sortRatings(true);
    worstReviewed = sortRatings(false);
  }

  private List<Rating> sortRatings(boolean descending) {
    Comparator<Rating> descComparator = new Comparator<Rating>() {
      @Override
      public int compare(Rating o1, Rating o2) {
        return Integer.compare(o1.getScore(), o2.getScore());
      }
    };

    Comparator<Rating> ascComparator = new Comparator<Rating>() {
      @Override
      public int compare(Rating o1, Rating o2) {
        return Integer.compare(o2.getScore(), o1.getScore());
      }
    };

    List<Rating> ratings = new ArrayList<>(getRatings());
    Collections.sort(ratings, descending ? descComparator : ascComparator);
    return ratings;
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

  public List<Rating> getBestReviewed() {
    return bestReviewed;
  }

  public void setBestReviewed(List<Rating> bestReviewed) {
    this.bestReviewed = bestReviewed;
  }

  public List<Rating> getWorstReviewed() {
    return worstReviewed;
  }

  public void setWorstReviewed(List<Rating> worstReviewed) {
    this.worstReviewed = worstReviewed;
  }
}

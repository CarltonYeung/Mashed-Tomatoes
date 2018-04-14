package com.mashedtomatoes.media;

public enum Genre {
  ACTION("Action"),
  ADVENTURE("Adventure"),
  ANIMATION("Animation"),
  BIOGRAPHY("Biography"),
  COMEDY("Comedy"),
  CRIME("Crime"),
  DOCUMENTARY("Documentary"),
  DRAMA("Drama"),
  FAMILY("Family"),
  FANTASY("Fantasy"),
  GAMESHOW("Game show"),
  HISTORY("History"),
  HORROR("Horror"),
  MUSIC("Music"),
  MUSICAL("Musical"),
  MYSTERY("Mystery"),
  NEWS("News"),
  REALITYTV("Reality TV"),
  ROMANCE("Romance"),
  SCIFI("Science Fiction"),
  SHORT("Short"),
  SPORT("Sport"),
  TALKSHOW("Talk show"),
  THRILLER("Thriller"),
  WAR("War"),
  WESTERN("Western");

  private String name;

  Genre(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}

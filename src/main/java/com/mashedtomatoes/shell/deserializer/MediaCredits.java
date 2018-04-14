package com.mashedtomatoes.shell.deserializer;

import java.util.HashSet;
import java.util.Set;

public class MediaCredits {
  private Set<MediaCastMember> cast = new HashSet<>();
  private Set<MediaCrewMember> crew = new HashSet<>();

  public Set<MediaCastMember> getCast() {
    return cast;
  }

  public Set<MediaCrewMember> getCrew() {
    return crew;
  }
}



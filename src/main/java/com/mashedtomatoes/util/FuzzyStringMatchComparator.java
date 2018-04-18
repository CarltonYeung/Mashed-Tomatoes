package com.mashedtomatoes.util;

import info.debatty.java.stringsimilarity.JaroWinkler;
import java.util.Comparator;
import java.util.function.Function;

public class FuzzyStringMatchComparator<T> implements Comparator<T> {
  private final String matchText;
  private final JaroWinkler jw;
  private final Function<T, String> matchGetter;

  public FuzzyStringMatchComparator(String matchText, Function<T, String> matchGetter) {
    this.matchText = matchText;
    this.matchGetter = matchGetter;
    this.jw = new JaroWinkler();
  }

  @Override
  public int compare(T lhs, T rhs) {
    double lhsDistance = jw.similarity(matchGetter.apply(lhs), matchText);
    double rhsDistance = jw.similarity(matchGetter.apply(rhs), matchText);
    if (lhsDistance > rhsDistance) {
      return -1;
    }

    if (lhsDistance < rhsDistance) {
      return 1;
    }

    return 0;
  }
}

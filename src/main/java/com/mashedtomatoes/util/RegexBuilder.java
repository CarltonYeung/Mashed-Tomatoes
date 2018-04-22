package com.mashedtomatoes.util;

import java.util.List;

public class RegexBuilder {
  public static String buildMySQLRegex(List<String> parts) {
    StringBuilder builder = new StringBuilder();
    builder.append(".*(");
    builder.append(String.join("-", parts));
    builder.append(").*");
    return builder.toString();
  }
}

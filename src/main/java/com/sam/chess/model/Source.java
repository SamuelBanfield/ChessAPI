package com.sam.chess.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Source(String user, Site site) {

  // Regex matching user@lichess or user@chessdotcom
  private static final Pattern SOURCE_PATTERN = Pattern.compile("^(?<user>[^@]+)@(?<site>lichess|chessdotcom)$");

  public enum Site {
    LICHESS,
    CHESSDOTCOM
  }

  public static Source fromString(String source) {
    Matcher matcher = SOURCE_PATTERN.matcher(source);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid source: " + source);
    }
    return new Source(matcher.group("user"), Site.valueOf(matcher.group("site").toUpperCase()));
  }
};

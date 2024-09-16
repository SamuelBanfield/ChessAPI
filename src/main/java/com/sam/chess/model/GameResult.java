package com.sam.chess.model;

/**
 * The result of a game.
 */
public enum GameResult {
  WHITE_WIN,
  DRAW,
  BLACK_WIN;

  public static GameResult fromString(final String result) {
    return switch (result) {
      case "1-0" -> WHITE_WIN;
      case "1/2-1/2" -> DRAW;
      case "0-1" -> BLACK_WIN;
      default -> throw new IllegalArgumentException("Unknown result: " + result);
    };
  }
}

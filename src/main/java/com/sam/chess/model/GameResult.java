package com.sam.chess.model;

/**
 * The result of a game.
 */
public enum GameResult {
  WHITE_WIN("1-0"),
  DRAW("1/2-1/2"),
  BLACK_WIN("0-1");

  private final String _result;

  @Override
  public String toString() {
    return _result;
  }

  GameResult(final String result) {
    _result = result;
  }

  public static GameResult fromString(final String result) {
    for (GameResult gameResult : GameResult.values()) {
      if (gameResult._result.equals(result)) {
        return gameResult;
      }
    }
    throw new IllegalArgumentException("Unknown result: " + result);
  }
}

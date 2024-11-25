package com.sam.chess.model;

public record PositionNote(String note) {

  public static final PositionNote EMPTY = new PositionNote("");
}

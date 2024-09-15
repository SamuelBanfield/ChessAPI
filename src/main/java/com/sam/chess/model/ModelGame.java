package com.sam.chess.model;

import java.util.List;

public record ModelGame(String whitePlayer, String blackPlayer, List<ModelMove> moves) {
}

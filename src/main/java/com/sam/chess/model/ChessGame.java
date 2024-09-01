package com.sam.chess.model;

import java.util.List;

public record ChessGame(String whitePlayer, String blackPlayer, List<ChessMove> moves) {
}

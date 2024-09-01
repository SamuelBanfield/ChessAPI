package com.sam.chess.model;

import java.util.List;

public class ChessGame {

    private String _whitePlayer;

    private String _blackPlayer;

    private List<ChessMove> _moves;

    public ChessGame(final String whitePlayer, final String blackPlayer, final List<ChessMove> moves) {
        _whitePlayer = whitePlayer;
        _blackPlayer = blackPlayer;
        _moves = moves;
    }
}

package com.sam.chess.model;

public class ChessMove {

    private String _move;
    private String _startingFEN;
    private String _endingFEN;

    public ChessMove(final String move, final String startingFEN, final String endingFEN) {
        _move = move;
        _startingFEN = startingFEN;
        _endingFEN = endingFEN;
    }

    public String getMove() {
        return _move;
    }

    public String getStartingFEN() {
        return _startingFEN;
    }

    public String getEndingFEN() {
        return _endingFEN;
    }

}

package com.sam.chess.client;

import java.io.IOException;
import java.util.List;

import com.sam.chess.model.ChessGame;

public interface ChessClient {

    List<ChessGame> getGames(final String userId) throws IOException, InterruptedException;

}

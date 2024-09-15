package com.sam.chess.client;

import java.io.IOException;
import java.util.List;

import com.sam.chess.model.ModelGame;

public interface ChessClient {

  List<ModelGame> getGames(final String userId) throws IOException, InterruptedException;

}

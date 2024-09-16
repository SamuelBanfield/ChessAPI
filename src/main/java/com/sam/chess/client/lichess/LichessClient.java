package com.sam.chess.client.lichess;

import static com.sam.chess.model.GameResult.BLACK_WIN;
import static com.sam.chess.model.GameResult.DRAW;
import static com.sam.chess.model.GameResult.WHITE_WIN;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sam.chess.client.ChessClient;
import com.sam.chess.model.GameResult;
import com.sam.chess.model.ModelGame;
import com.sam.chess.model.ModelMove;

import chariot.Client;
import chariot.model.Game;
import io.github.wolfraam.chessgame.ChessGame;
import io.github.wolfraam.chessgame.notation.NotationType;

/**
 * Wrapper around client for lichess API.
 */
@Component
public class LichessClient implements ChessClient {
  
  private Client _lichess;

  @Bean
  private static Client client() {
    return Client.basic();
  }

  @Autowired
  public LichessClient(final Client lichess) {
    _lichess = lichess;
  }

  @Override
  public List<ModelGame> getGames(final String userId) {
    return _lichess.games()
      .byUserId(userId, filter -> filter.until(ZonedDateTime.now()).max(200))
      .stream()
      .map(this::toChessGame)
      .toList();
  }

  private ModelGame toChessGame(final Game game) {
    return new ModelGame(
      game.players().white().name(),
      game.players().black().name(),
      result(game),
      movesFromPGN(game.moves()));
  }

  private List<ModelMove> movesFromPGN(final String movesString) {
    List<ModelMove> modelMoves = new ArrayList<>();
    String[] moves = movesString.split(" ");
    ChessGame game = new ChessGame();
    for (String move : moves) {
      String startingFEN = game.getFen();
      game.playMove(NotationType.SAN, move);
      String endingFEN = game.getFen();
      modelMoves.add(new ModelMove(move, startingFEN, endingFEN));
    }
    return modelMoves;
  }

  private GameResult result(final Game game) {
    return switch (game.winner()) {
      case white -> WHITE_WIN;
      case black -> BLACK_WIN;
      case null -> DRAW;
    };
  }

}

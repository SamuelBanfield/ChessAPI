package com.sam.chess.client.lichess;

import static com.sam.chess.model.GameResult.BLACK_WIN;
import static com.sam.chess.model.GameResult.DRAW;
import static com.sam.chess.model.GameResult.WHITE_WIN;
import static com.sam.chess.model.Source.Site.LICHESS;

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
import chariot.model.Enums.GameVariant;
import chariot.model.Enums.PerfType;
import io.github.wolfraam.chessgame.ChessGame;
import io.github.wolfraam.chessgame.move.IllegalMoveException;
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
      .byUserId(userId, filter -> filter
        .perfType(PerfType.bullet, PerfType.blitz, PerfType.rapid, PerfType.classical)
        .until(ZonedDateTime.now())
        .max(200)
      )
      .stream()
      .filter(game -> game.variant().equals(GameVariant.standard))
      .map(this::toChessGame)
      .toList();
  }

  private ModelGame toChessGame(final Game game) {
    return new ModelGame(
      game.players().white().name(),
      game.players().black().name(),
      result(game),
      movesFromPGN(game.moves()),
      game.id(),
      LICHESS);
  }

  private List<ModelMove> movesFromPGN(final String movesString) {
    try {
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
    catch (IllegalMoveException e) {
      System.err.println("Error parsing PGN: " + movesString);
      return List.of();
    }
  }

  private GameResult result(final Game game) {
    return switch (game.winner()) {
      case white -> WHITE_WIN;
      case black -> BLACK_WIN;
      case null -> DRAW;
    };
  }

}

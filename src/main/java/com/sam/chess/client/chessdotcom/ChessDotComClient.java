package com.sam.chess.client.chessdotcom;

import static com.sam.chess.model.GameResult.BLACK_WIN;
import static com.sam.chess.model.GameResult.DRAW;
import static com.sam.chess.model.GameResult.WHITE_WIN;
import static com.sam.chess.model.GameResult.fromString;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.chess.client.ChessClient;
import com.sam.chess.client.chessdotcom.ArchiveResponse.GameResponse;
import com.sam.chess.model.GameResult;
import com.sam.chess.model.ModelGame;
import com.sam.chess.model.ModelMove;

import io.github.wolfraam.chessgame.ChessGame;
import io.github.wolfraam.chessgame.notation.NotationType;
import io.github.wolfraam.chessgame.pgn.PGNImporter;
import io.github.wolfraam.chessgame.pgn.PGNTag;

@Component
public class ChessDotComClient implements ChessClient {

  private final ChessDotComHTTPClient _client;

  private final PGNImporter _pgnImporter;

  private final ObjectMapper _objectMapper = new ObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @Autowired
  public ChessDotComClient(final ChessDotComHTTPClient client) {
    _client = client;
    _pgnImporter = new PGNImporter();
    _pgnImporter.setOnError(System.out::println);
    _pgnImporter.setOnWarning(System.out::println);
  }

  @Override
  public List<ModelGame> getGames(final String userId) throws IOException, InterruptedException {
    return parseArchives(userId).stream()
      .limit(2)
      .map(archive -> {
          try {
              return parseGames(archive);
          } catch (IOException | InterruptedException e) {
              throw new RuntimeException(e);
          }
      })
      .flatMap(List::stream)
      .toList();
  }

  private List<String> parseArchives(final String userId) throws IOException, InterruptedException {
    return _objectMapper.readValue(_client.getArchivesAvailable(userId).body(), ArchivesAvailableResponse.class).archives();
  }

  private List<ModelGame> parseGames(final String archive) throws IOException, InterruptedException {
    return _objectMapper.readValue(_client.getGamesFromArchive(archive).body(), ArchiveResponse.class).games()
      .stream()
      .map(this::parseGame)
      .toList();
  }

  private ModelGame parseGame(final GameResponse game) {
    List<ModelMove> modelMoves = new ArrayList<>();
    GameResult[] result = new GameResult[1];
    _pgnImporter.setOnGame((parsedGame) -> {
      result[0] = fromString(parsedGame.getPGNData().getPGNTagValue(PGNTag.RESULT));
      List<String> moves = parsedGame.getNotationList(NotationType.SAN);
      ChessGame replay = new ChessGame();
      for (String move : moves) {
        String startingFEN = replay.getFen();

        replay.playMove(NotationType.SAN, move);
        String endingFEN = replay.getFen();
        modelMoves.add(new ModelMove(move.toString(), startingFEN, endingFEN));
      }
    });
    _pgnImporter.run(new ByteArrayInputStream(game.pgn().getBytes(StandardCharsets.UTF_8)));
    return new ModelGame(game.white().username(), game.black().username(), result[0], modelMoves, game.url());
  }

}

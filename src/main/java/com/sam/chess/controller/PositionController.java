package com.sam.chess.controller;

import static com.sam.chess.db.entity.GameMoveRepository.matchesPosition;
import static com.sam.chess.db.entity.GameMoveRepository.matchesSource;
import static com.sam.chess.model.Colour.BLACK;
import static com.sam.chess.model.Colour.WHITE;
import static java.util.Collections.emptyList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sam.chess.client.chessdotcom.ChessDotComClient;
import com.sam.chess.client.lichess.LichessClient;
import com.sam.chess.db.entity.GameMoveEntity;
import com.sam.chess.db.entity.GameMoveRepository;
import com.sam.chess.db.entity.MoveEntity;
import com.sam.chess.model.Colour;
import com.sam.chess.model.GameResult;
import com.sam.chess.model.ModelGame;
import com.sam.chess.model.ModelMove;
import com.sam.chess.model.Source;

@RestController
@RequestMapping("/game")
public class PositionController {

  // Regex matching the important features of a FEN
  private static final Pattern GAME_PATTERN = Pattern.compile("([rnbqkpRNBQKP1-8]+/){7}[rnbqkpRNBQKP1-8]+ [wb]");

  record MoveWithFrequency(ModelMove move, int whiteWins, int draws, int blackWins) {};

	@Autowired
	private LichessClient _lichessClient;
	@Autowired
	private ChessDotComClient _chessDotComClient;

  @Autowired
  private GameMoveRepository _gameMoveRepository;

	@RequestMapping(path = "/lichess/{userName}", method = GET)
	@ResponseBody
	public List<ModelGame> getLichessGames(@PathVariable("userName") final String userName) {
		return _lichessClient.getGames(userName);
	}

	@RequestMapping(path = "/chessdotcom/{userName}", method = GET)
	@ResponseBody
	public List<ModelGame> getChessDotComGames(@PathVariable("userName") final String userName) throws IOException, InterruptedException {
    return _chessDotComClient.getGames(userName);
	}

  @RequestMapping(path = "/", method = GET)
  @ResponseBody
  public List<MoveWithFrequency> gamesFromPosition(
    @RequestParam("fen") final String position,
    @RequestParam("colour") final String colourString,
    @RequestParam("sources") final List<String> sourcesRaw
  ) {
    final Matcher matcher = GAME_PATTERN.matcher(position);
    if (!matcher.find()) {
      throw new IllegalArgumentException("Invalid FEN: " + position);
    }
    final Colour colour = colourString.equals("black") ? BLACK : WHITE;
    final List<Source> sources = sourcesRaw.stream()
      .map(Source::fromString)
      .toList();

    Map<String, Map<GameResult, List<GameMoveEntity>>> movesWithFrequencies =  _gameMoveRepository.findAll(matchesPosition(matcher.group()).and(matchesSource(sources, colour))).stream()
      .collect(Collectors.groupingBy(
        move -> move.getMove().name(),
        Collectors.groupingBy(
          move -> move.getGame().getResult()
        )
      ));

    return movesWithFrequencies.values().stream()
      .map(frequencies -> {
        MoveEntity move = frequencies.values().stream()
          .flatMap(List::stream)
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("No moves for entry"))
          .getMove();
        
        return new MoveWithFrequency(
          new ModelMove(move.name(), move.start(), move.end()),
          frequencies.getOrDefault(GameResult.WHITE_WIN, emptyList()).size(),
          frequencies.getOrDefault(GameResult.DRAW, emptyList()).size(),
          frequencies.getOrDefault(GameResult.BLACK_WIN, emptyList()).size());
      })
      .toList();
  }

}

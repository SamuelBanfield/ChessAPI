package com.sam.chess.controller;

import static com.sam.chess.model.Colour.BLACK;
import static com.sam.chess.model.Colour.WHITE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.util.List;
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
import com.sam.chess.db.entity.MoveRepository;
import com.sam.chess.model.Colour;
import com.sam.chess.model.ModelGame;
import com.sam.chess.model.ModelMove;

@RestController
@RequestMapping("/game")
public class PositionController {

  // Regex matching the important features of a FEN
  private static final Pattern GAME_PATTERN = Pattern.compile("([rnbqkpRNBQKP1-8]+/){7}[rnbqkpRNBQKP1-8]+ [wb]");
  // Regex matching user@lichess or user@chessdotcom
  private static final Pattern SOURCE_PATTERN = Pattern.compile("^(?<user>[^@]+)@(?<source>lichess|chessdotcom)$");

	@Autowired
	private LichessClient _lichessClient;
	@Autowired
	private ChessDotComClient _chessDotComClient;

  @Autowired
  private MoveRepository _moveRepository;

	@RequestMapping(path = "/games/lichess/{userName}", method = GET)
	@ResponseBody
	public List<ModelGame> getLichessGames(@PathVariable("userName") final String userName) {
		return _lichessClient.getGames(userName);
	}

	@RequestMapping(path = "/games/chessdotcom/{userName}", method = GET)
	@ResponseBody
	public List<ModelGame> getChessDotComGames(@PathVariable("userName") final String userName) throws IOException, InterruptedException {
    return _chessDotComClient.getGames(userName);
	}

  @RequestMapping(path = "/games", method = GET)
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
      .map(source -> {
        final Matcher sourceMatcher = SOURCE_PATTERN.matcher(source);
        if (!sourceMatcher.find()) {
          throw new IllegalArgumentException("Invalid source: " + source);
        }
        return new Source(sourceMatcher.group("user"), sourceMatcher.group("source"));
      })
      .toList();
    return _moveRepository.findAllByStartLike(matcher.group() + "%")
      .stream()
      .map(move -> new MoveWithFrequency(new ModelMove(move.name(), move.start(), move.end()), move.whiteWins(), move.draws(), move.blackWins()))
      .toList();    
  }

  record Source(String user, String site) {};

  record MoveWithFrequency(ModelMove move, int whiteWins, int draws, int blackWins) {};

}

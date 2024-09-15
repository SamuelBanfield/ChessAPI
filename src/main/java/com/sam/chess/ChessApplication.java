package com.sam.chess;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sam.chess.client.chessdotcom.ChessDotComClient;
import com.sam.chess.client.lichess.LichessClient;
import com.sam.chess.db.entity.MoveRepository;
import com.sam.chess.model.ModelGame;
import com.sam.chess.model.ModelMove;
import com.sam.chess.shredder.Shredder;

@SpringBootApplication
@RestController
public class ChessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessApplication.class, args);
	}

  private static final Pattern GAME_PATTERN = Pattern.compile("([rnbqkpRNBQKP1-8]+/){7}[rnbqkpRNBQKP1-8]+ [wb]");

	@Autowired
	private LichessClient _lichessClient;

	@Autowired
	private ChessDotComClient _chessDotComClient;

  @Autowired
  private Shredder _shredder;

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

	@RequestMapping(path = "/games/lichess/{userName}/import", method = POST)
	public int importLichessGames(@PathVariable("userName") final String userName) {
		return _shredder.shred(_lichessClient.getGames(userName));
	}

	@RequestMapping(path = "/games/chessdotcom/{userName}/import", method = POST)
	public int importChessDotComGames(@PathVariable("userName") final String userName) throws IOException, InterruptedException {
		return _shredder.shred(_chessDotComClient.getGames(userName));
	}

  @RequestMapping(path = "/games", method = GET)
  @ResponseBody
  public List<MoveWithFrequency> gamesFromPosition(@RequestParam("fen") final String position) {
    Matcher matcher = GAME_PATTERN.matcher(position);
    if (!matcher.find()) {
      throw new IllegalArgumentException("Invalid FEN: " + position);
    }
    return _moveRepository.findAllByStartLike(matcher.group() + "%")
      .stream()
      .map(move -> new MoveWithFrequency(new ModelMove(move.name(), move.start(), move.end()), move.occurrences()))
      .toList();    
  }

  record MoveWithFrequency(ModelMove move, int frequency) {}

	@RequestMapping(path = "/status", method = GET)
	public String status() {
		return "OK";
	}

}

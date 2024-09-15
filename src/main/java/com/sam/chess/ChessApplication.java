package com.sam.chess;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sam.chess.client.chessdotcom.ChessDotComClient;
import com.sam.chess.client.lichess.LichessClient;
import com.sam.chess.model.ModelGame;
import com.sam.chess.shredder.Shredder;

@SpringBootApplication
@RestController
public class ChessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessApplication.class, args);
	}

	@Autowired
	private LichessClient _lichessClient;

	@Autowired
	private ChessDotComClient _chessDotComClient;

  @Autowired
  private Shredder _shredder;

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

	@RequestMapping(path = "/status", method = GET)
	public String status() {
		return "OK";
	}

}

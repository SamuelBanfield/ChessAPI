package com.sam.chess;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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
import com.sam.chess.model.ChessGame;

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


	@RequestMapping(path = "/games/lichess/{userName}/", method = GET)
	@ResponseBody
	public List<ChessGame> getLichessGames(@PathVariable("userName") final String userName) {
		return _lichessClient.getGames(userName);
	}

	@RequestMapping(path = "/games/chessdotcom/{userName}/", method = GET)
	@ResponseBody
	public List<ChessGame> getChessDotComGames(@PathVariable("userName") final String userName) {
		try {
			return _chessDotComClient.getGames(userName);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return List.of();
		}
	}

	@RequestMapping(path = "/status", method = GET)
	public String status() {
		return "OK";
	}

}

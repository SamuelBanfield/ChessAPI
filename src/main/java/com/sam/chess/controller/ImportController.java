package com.sam.chess.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.chess.client.chessdotcom.ChessDotComClient;
import com.sam.chess.client.lichess.LichessClient;
import com.sam.chess.shredder.Shredder;

@RestController
@RequestMapping("/import")
public class ImportController {

	@Autowired
	private LichessClient _lichessClient;
	@Autowired
	private ChessDotComClient _chessDotComClient;
	@Autowired
	private Shredder _shredder;

	@RequestMapping(path = "/lichess/{userName}", method = POST)
	public int importLichessGames(@PathVariable("userName") final String userName) {
		System.out.println("Importing lichess games for user " + userName);
		int total = _shredder.shred(_lichessClient.getGames(userName));
		System.out.println("Imported " + total + " games for user " + userName);
		return total;
	}

	@RequestMapping(path = "/chessdotcom/{userName}", method = POST)
	public int importChessDotComGames(@PathVariable("userName") final String userName) throws IOException, InterruptedException {
		System.out.println("Importing chess.com games for user " + userName);
		int total = _shredder.shred(_chessDotComClient.getGames(userName));
		System.out.println("Imported " + total + " games for user " + userName);
		return total;
	}

}

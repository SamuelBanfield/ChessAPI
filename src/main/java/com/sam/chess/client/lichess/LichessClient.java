package com.sam.chess.client.lichess;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sam.chess.client.ChessClient;
import com.sam.chess.model.ChessGame;

import chariot.Client;
import chariot.model.Game;

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
    public List<ChessGame> getGames(final String userId) {
        return _lichess.games()
            .byUserId(userId, filter -> filter.until(ZonedDateTime.now()).max(200))
            .stream()
            .map(this::toChessGame)
            .toList();
    }

    private ChessGame toChessGame(final Game game) {
        return new ChessGame(
            game.players().white().name(),
            game.players().black().name(),
            List.of());
    }

}

package com.sam.chess.client.chessdotcom;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.chess.client.ChessClient;
import com.sam.chess.model.ChessGame;

@Component
public class ChessDotComClient implements ChessClient {

    private final ChessDotComHTTPClient _client;

    private final ObjectMapper _objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    public ChessDotComClient(final ChessDotComHTTPClient client) {
        _client = client;
    }

    @Override
    public List<ChessGame> getGames(final String userId) throws IOException, InterruptedException {
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

    private List<ChessGame> parseGames(final String archive) throws IOException, InterruptedException {
        return _objectMapper.readValue(_client.getGamesFromArchive(archive).body(), ArchiveResponse.class).games()
            .stream()
            .map(game -> new ChessGame(game.white().username(), game.black().username(), List.of()))
            .toList();
    }

}

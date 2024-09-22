package com.sam.chess.client.chessdotcom;

import static com.sam.chess.model.GameResult.BLACK_WIN;
import static com.sam.chess.model.GameResult.DRAW;
import static com.sam.chess.model.GameResult.WHITE_WIN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sam.chess.model.ModelGame;

/**
 * Tests for {@link ChessDotComClient}.
 */
@SpringBootTest
public class TestChessDotComClient {

    @MockBean
    private ChessDotComHTTPClient _httpClient;

    @Autowired
    private ChessDotComClient _chessDotComClient;

    @SuppressWarnings("unchecked")
    @Test
    void testChessDotComClient() throws IOException, InterruptedException {
        HttpResponse<String> archivesResponse = mock(HttpResponse.class);
        when(archivesResponse.body()).thenReturn(new String(getClass().getResourceAsStream("archives.json").readAllBytes()));
        when(_httpClient.getArchivesAvailable("tfdethh")).thenReturn(archivesResponse);

        HttpResponse<String> gamesResponse = mock(HttpResponse.class);
        when(gamesResponse.body()).thenReturn(new String(getClass().getResourceAsStream("games_2020_07.json").readAllBytes()));
        when(_httpClient.getGamesFromArchive("https://api.chess.com/pub/player/tfdethh/games/2020/07")).thenReturn(gamesResponse);

        List<ModelGame> games = _chessDotComClient.getGames("tfdethh");
        assertEquals(243, games.size());

        assertEquals(109, games.stream().filter(game -> WHITE_WIN.equals(game.result())).count());
        assertEquals(9, games.stream().filter(game -> DRAW.equals(game.result())).count());
        assertEquals(125, games.stream().filter(game -> BLACK_WIN.equals(game.result())).count());
    }

}

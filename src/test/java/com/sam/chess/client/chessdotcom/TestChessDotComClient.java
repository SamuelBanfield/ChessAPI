package com.sam.chess.client.chessdotcom;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * Tests for {@link ChessDotComClient}.
 */
@SpringBootTest
public class TestChessDotComClient {

    @MockBean
    private ChessDotComHTTPClient _httpClient;

    @Autowired
    private ChessDotComClient _chessDotComClient;

    @Test
    void testChessDotComClient() throws IOException, InterruptedException {
        HttpResponse<String> archivesResponse = mock(HttpResponse.class);
        when(archivesResponse.body()).thenReturn(new String(getClass().getResourceAsStream("archives.json").readAllBytes()));
        when(_httpClient.getArchivesAvailable("tfdethh")).thenReturn(archivesResponse);

        HttpResponse<String> gamesResponse = mock(HttpResponse.class);
        when(gamesResponse.body()).thenReturn(new String(getClass().getResourceAsStream("games_2020_07.json").readAllBytes()));
        when(_httpClient.getGamesFromArchive("https://api.chess.com/pub/player/tfdethh/games/2020/07")).thenReturn(gamesResponse);

        assertEquals(243, _chessDotComClient.getGames("tfdethh").size());
    }

}

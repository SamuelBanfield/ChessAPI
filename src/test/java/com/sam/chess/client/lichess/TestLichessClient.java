package com.sam.chess.client.lichess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link LichessClient}.
 */
@SpringBootTest
public class TestLichessClient {

    @Autowired
    private LichessClient _lichessClient;

    @Test
    void testLichessClient() {
      System.out.println(_lichessClient.getGames("samjban"));
    }

}
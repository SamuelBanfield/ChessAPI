package com.sam.chess.client.lichess;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sam.chess.model.GameResult;
import com.sam.chess.model.ModelGame;

/**
 * Tests for {@link LichessClient}.
 * The "samjban_test" account has 2 wins and one loss.
 */
@SpringBootTest
public class TestLichessClient {

    @Autowired
    private LichessClient _lichessClient;

    @Test
    void testLichessClient() {
      final List<ModelGame> games = _lichessClient.getGames("samjban_test");
      assertEquals(3, games.size());
      assertEquals(2, games.stream().filter(game -> game.result().equals(GameResult.WHITE_WIN)).count());
      assertEquals(1, games.stream().filter(game -> game.result().equals(GameResult.BLACK_WIN)).count());
    }

}
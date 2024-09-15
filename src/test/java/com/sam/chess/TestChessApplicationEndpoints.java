package com.sam.chess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sam.chess.model.ModelGame;

@SpringBootTest
public class TestChessApplicationEndpoints {

    @Autowired
    private ChessApplication _chessApplication;

    @Test
    void testLichessEndpoint() {
        List<ModelGame> lichessGames = _chessApplication.getLichessGames("samjban");
        assertFalse(lichessGames.isEmpty());
    }

    @Test
    void testChessDotComEndpoint() throws IOException, InterruptedException {
        List<ModelGame> chessDotComGames = _chessApplication.getChessDotComGames("tfdethh");
        assertFalse(chessDotComGames.isEmpty());
    }

    @Test
    void testLichessImport() throws Exception {
      assertTrue(_chessApplication.importLichessGames("samjban") > 0);
    }

    @Test
    void testChessDotComImport() throws Exception {
      assertTrue(_chessApplication.importChessDotComGames("tfdethh") > 0);
    }

}

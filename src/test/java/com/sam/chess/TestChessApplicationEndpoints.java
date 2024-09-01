package com.sam.chess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sam.chess.model.ChessGame;

@SpringBootTest
public class TestChessApplicationEndpoints {

    @Autowired
    private ChessApplication _chessApplication;

    @Test
    void testLichessEndpoint() {
        List<ChessGame> lichessGames = _chessApplication.getLichessGames("samjban");
        assertFalse(lichessGames.isEmpty());
    }

    @Test
    void testChessDotComEndpoint() {
        List<ChessGame> chessDotComGames = _chessApplication.getChessDotComGames("tfdethh");
        assertFalse(chessDotComGames.isEmpty());
    }

}

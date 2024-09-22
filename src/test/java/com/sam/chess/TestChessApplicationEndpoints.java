package com.sam.chess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sam.chess.controller.ImportController;
import com.sam.chess.model.ModelGame;

@SpringBootTest
public class TestChessApplicationEndpoints {

  @Autowired
  private ImportController _chessApplication;

  @Test
  void testLichessImport() throws Exception {
    assertTrue(_chessApplication.importLichessGames("samjban") > 0);
    assertTrue(_chessApplication.importLichessGames("samjban") == 0);
  }

  @Test
  void testChessDotComImport() throws Exception {
    assertTrue(_chessApplication.importChessDotComGames("tfdethh") > 0);
    assertTrue(_chessApplication.importChessDotComGames("tfdethh") == 0);
  }

}

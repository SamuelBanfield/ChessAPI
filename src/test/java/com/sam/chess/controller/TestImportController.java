package com.sam.chess.controller;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestImportController {

  @Autowired
  private ImportController _importController;

  @Test
  void testLichessImport() throws Exception {
    assertTrue(_importController.importLichessGames("samjban") > 0);
    assertTrue(_importController.importLichessGames("samjban") == 0);
  }

  @Test
  void testChessDotComImport() throws Exception {
    assertTrue(_importController.importChessDotComGames("tfdethh") > 0);
    assertTrue(_importController.importChessDotComGames("tfdethh") == 0);
  }

}

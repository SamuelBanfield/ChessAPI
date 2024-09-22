package com.sam.chess.script;

import org.apache.logging.log4j.util.Timer;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.List;

import com.sam.chess.ChessApplication;
import com.sam.chess.controller.ImportController;

/**
 * Script to import games from all accounts.
 */
public class PopulateDB {

  private static final List<String> CHESS_DOT_COM_USERNAMES = List.of("wertsy10", "milkygerm", "tfdethh");
  private static final List<String> LICHESS_USERNAMES = List.of("wertsy10", "samjban");

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext context = SpringApplication.run(ChessApplication.class, args);
    ImportController app = context.getBean(ImportController.class);

    CHESS_DOT_COM_USERNAMES.forEach(userName -> {
      System.out.println("Importing chess.com games for user: " + userName);
      try {
        app.importChessDotComGames(userName);
      }
      catch (Throwable t) {
        throw new RuntimeException("Failed to import games for user: " + userName, t);
      }
    });
    LICHESS_USERNAMES.forEach(userName -> {
      System.out.println("Importing lichess games for user: " + userName);
      try {
        app.importLichessGames(userName);
      }
      catch (Throwable t) {
        throw new RuntimeException("Failed to import games for user: " + userName, t);
      }
    });

    context.close();

  }

}

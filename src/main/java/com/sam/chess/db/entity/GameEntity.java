package com.sam.chess.db.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.sam.chess.model.GameResult;
import com.sam.chess.model.Source.Site;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "game")
public class GameEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long _id;

  @Column(name = "white_player")
  private String _whitePlayer;

  @Column(name = "black_player")
  private String _blackPlayer;

  @Column(name = "result")
  private GameResult _result;

  // Underscore missing for JPARepository compatibility
  // Uniquely identifies this game to prevent reimporting
  @Column(name = "source")
  private String source;

  @Column(name = "site")
  private Site _site;

  public static GameEntity create(final String whitePlayer, final String blackPlayer, final GameResult result, final String source, final Site site) {
    GameEntity entity = new GameEntity();
    entity._whitePlayer = whitePlayer;
    entity._blackPlayer = blackPlayer;
    entity._result = result;
    entity.source = source;
    entity._site = site;
    return entity;
  }

  public GameResult getResult() {
    return _result;
  }

}

package com.sam.chess.db.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

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
  private String _result;

  // Underscore missing for JPARepository compatibility
  @Column(name = "source")
  private String source;

  public static GameEntity create(final String whitePlayer, final String blackPlayer, final String result, final String source) {
    GameEntity entity = new GameEntity();
    entity._whitePlayer = whitePlayer;
    entity._blackPlayer = blackPlayer;
    entity._result = result;
    entity.source = source;
    return entity;
  }

}

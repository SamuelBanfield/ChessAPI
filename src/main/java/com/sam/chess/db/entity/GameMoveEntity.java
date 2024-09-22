package com.sam.chess.db.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Join entity for games and moves.
 */
@Entity(name = "game_move")
public class GameMoveEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long _id;

  @JoinColumn(name = "game_id")
  @ManyToOne
  private GameEntity _game;

  @JoinColumn(name = "move_id")
  @ManyToOne
  private MoveEntity _move;

  public static GameMoveEntity create(final GameEntity game, final MoveEntity move) {
    GameMoveEntity entity = new GameMoveEntity();
    entity._game = game;
    entity._move = move;
    return entity;
  }

  public GameEntity getGame() {
    return _game;
  }

  public MoveEntity getMove() {
    return _move;
  }

}

package com.sam.chess.db.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.sam.chess.model.GameResult;
import com.sam.chess.model.ModelMove;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * A move in a game of chess.
 */
@Entity(name = "move")
public class MoveEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long _id;

    // Underscore missing for JPARepository compatibility
    @Column(name = "start_fen")
    private String start;

    // Underscore missing for JPARepository compatibility
    @Column(name = "end_fen")
    private String end;

    @Column(name = "name")
    private String _name;

    public static MoveEntity create(final ModelMove move) {
      MoveEntity entity = new MoveEntity();
      entity.start = move.startingFEN();
      entity.end = move.endingFEN();
      entity._name = move.move();
      return entity;
    }

    public MoveEntity() {}

    public String start() {
      return start;
    }

    public String end() {
      return end;
    }

    public String name() {
      return _name;
    }

}

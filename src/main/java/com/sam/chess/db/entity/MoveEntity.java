package com.sam.chess.db.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

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

    @Column(name = "start_fen")
    private String start;

    @Column(name = "end_fen")
    private String end;

    @Column(name = "name")
    private String _name;

    @Column(name = "occurrences")
    private int _occurrences;

    public void addOccurrence() {
      _occurrences++;
    }

    public MoveEntity() {}

    public static MoveEntity create(final ModelMove move) {
      MoveEntity entity = new MoveEntity();
      entity.start = move.startingFEN();
      entity.end = move.endingFEN();
      entity._name = move.move();
      entity._occurrences = 1;
      return entity;
    }

    public String start() {
      return start;
    }

    public String end() {
      return end;
    }

    public String name() {
      return _name;
    }

    public int occurrences() {
      return _occurrences;
    }
}

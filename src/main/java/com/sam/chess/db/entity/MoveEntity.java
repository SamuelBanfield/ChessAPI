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

    @Column(name = "white_wins")
    private int _whiteWins = 0;

    @Column(name = "draws")
    private int _draws = 0;

    @Column(name = "black_wins")
    private int _blackWins = 0;

    public static MoveEntity create(final ModelMove move, final GameResult result) {
      MoveEntity entity = new MoveEntity();
      entity.start = move.startingFEN();
      entity.end = move.endingFEN();
      entity._name = move.move();
      switch (result) {
        case WHITE_WIN -> entity._whiteWins = 1;
        case DRAW -> entity._draws = 1;
        case BLACK_WIN -> entity._blackWins = 1;
      }
      return entity;
    }

    public void addResult(final GameResult result) {
      switch (result) {
        case WHITE_WIN -> _whiteWins++;
        case DRAW -> _draws++;
        case BLACK_WIN -> _blackWins++;
      }
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

    public int whiteWins() {
      return _whiteWins;
    }

    public int draws() {
      return _draws;
    }

    public int blackWins() {
      return _blackWins;
    }
}

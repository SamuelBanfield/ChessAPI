package com.sam.chess.db.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A move in a game of chess.
 */
@Entity(name = "move")
public class MoveEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long _id;

    @Column(name = "number")
    private int _number;

    @Column(name = "name")
    private String _name;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity _game;

    public MoveEntity() {}

    public MoveEntity(final GameEntity game, final int number, final String name) {
        _game = game;
        _number = number;
        _name = name;
    }

    public GameEntity getGame() {
        return _game;
    }
}

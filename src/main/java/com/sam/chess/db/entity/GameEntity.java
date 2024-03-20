package com.sam.chess.db.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * A game of chess.
 */
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
    
    public GameEntity() {}
    
    public GameEntity(final String whitePlayer, final String blackPlayer) {
        _whitePlayer = whitePlayer;
        _blackPlayer = blackPlayer;
    }

    public String getWhitePlayer() {
        return _whitePlayer;
    }
    public String getBlackPlayer() {
        return _blackPlayer;
    }
    
}

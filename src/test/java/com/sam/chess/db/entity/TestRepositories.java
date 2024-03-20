package com.sam.chess.db.entity;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRepositories {

    @Autowired
    private MoveRepository _moveRepository;

    @Autowired
    private GameRepository _gameRepository;

    @Test
    public void testGameRepository() {
        _gameRepository.save(new GameEntity("white", "black"));
        GameEntity game = getOnlyElement(_gameRepository.findAll());
        assertEquals("white", game.getWhitePlayer());
        assertEquals("black", game.getBlackPlayer());
    }

    @Test
    public void testMoveRepository() {
        GameEntity game = _gameRepository.save(new GameEntity("white", "black"));
        _moveRepository.save(new MoveEntity(game, 1, "e4"));
        MoveEntity move = getOnlyElement(_moveRepository.findAll());
        assertEquals("white", move.getGame().getWhitePlayer());
    }
    
}

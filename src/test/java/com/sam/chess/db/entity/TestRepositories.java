package com.sam.chess.db.entity;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.sam.chess.model.GameResult.WHITE_WIN;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sam.chess.model.ModelMove;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class TestRepositories {

    @Autowired
    private MoveRepository _moveRepository;

    @Test
    void testMoveRepository() {
      _moveRepository.save(MoveEntity.create(new ModelMove("e4", "start", "end"), WHITE_WIN));
      MoveEntity move = getOnlyElement(_moveRepository.findAll());
      // assertEquals("white", move);
    }
    
}

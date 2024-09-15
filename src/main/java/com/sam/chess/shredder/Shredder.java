package com.sam.chess.shredder;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sam.chess.db.entity.MoveEntity;
import com.sam.chess.db.entity.MoveRepository;
import com.sam.chess.model.ModelGame;
import com.sam.chess.model.ModelMove;

@Component
public class Shredder {

  @Autowired
  private MoveRepository _moveRepository;

  public int shred(final Collection<ModelGame> games) {
    games.forEach(game -> {
      _moveRepository.saveAll(game.moves().stream().map(this::createOrUpdate).toList());
    });
    return games.size();
  }

  MoveEntity createOrUpdate(final ModelMove move) {
    return _moveRepository.findOneByStartAndEnd(move.startingFEN(), move.endingFEN())
      .map(entity -> {
        entity.addOccurrence();
        return entity;
      })
      .orElse(MoveEntity.create(move));
  }
}
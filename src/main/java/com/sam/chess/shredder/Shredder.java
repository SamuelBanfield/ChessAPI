package com.sam.chess.shredder;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sam.chess.db.entity.GameEntity;
import com.sam.chess.db.entity.GameRepository;
import com.sam.chess.db.entity.MoveEntity;
import com.sam.chess.db.entity.MoveRepository;
import com.sam.chess.model.GameResult;
import com.sam.chess.model.ModelGame;
import com.sam.chess.model.ModelMove;

@Component
public class Shredder {

  @Autowired
  private GameRepository _gameRepository;

  @Autowired
  private MoveRepository _moveRepository;

  public int shred(final Collection<ModelGame> games) {
    AtomicInteger shreddedGameCounter = new AtomicInteger(0);

    games.forEach(game -> {
      // TODO: some potential for threading here as long as there's not too much db locking
      if (_gameRepository.existsGameBySource(game.source())) {
        return;
      }
      _gameRepository.save(GameEntity.create(game.whitePlayer(), game.blackPlayer(), game.result().toString(), game.source()));
      _moveRepository.saveAll(game.moves().stream().map(move -> createOrUpdate(move, game.result())).toList());
      shreddedGameCounter.incrementAndGet();
    });
    return shreddedGameCounter.get();
  }

  MoveEntity createOrUpdate(final ModelMove move, final GameResult result) {
    return _moveRepository.findOneByStartAndEnd(move.startingFEN(), move.endingFEN())
      .map(entity -> {
        entity.addResult(result);
        return entity;
      })
      .orElse(MoveEntity.create(move, result));
  }
}
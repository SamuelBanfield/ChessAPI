package com.sam.chess.shredder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sam.chess.db.entity.GameEntity;
import com.sam.chess.db.entity.GameMoveEntity;
import com.sam.chess.db.entity.GameMoveRepository;
import com.sam.chess.db.entity.GameRepository;
import com.sam.chess.db.entity.MoveEntity;
import com.sam.chess.db.entity.MoveRepository;
import com.sam.chess.model.ModelGame;

@Component
public class Shredder {

  @Autowired
  private GameRepository _gameRepository;
  @Autowired
  private MoveRepository _moveRepository;
  @Autowired
  private GameMoveRepository _gameMoveRepository;

  public int shred(final Collection<ModelGame> games) {
    AtomicInteger shreddedGameCounter = new AtomicInteger(0);

    games.forEach(game -> {
      if (_gameRepository.existsGameBySource(game.source())) {
        return;
      }
      GameEntity savedGame = _gameRepository.save(GameEntity.create(game.whitePlayer(), game.blackPlayer(), game.result(), game.source()));
      List<MoveEntity> savedMoves = _moveRepository.saveAll(game.moves().stream().map(MoveEntity::create).toList());
      _gameMoveRepository.saveAll(savedMoves.stream().map(move -> GameMoveEntity.create(savedGame, move)).toList());
      shreddedGameCounter.incrementAndGet();
    });
    return shreddedGameCounter.get();
  }

}
package com.sam.chess.db.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Repository for moves.
 */
@Component
public interface GameRepository extends JpaRepository<GameEntity, Long> {
  
  boolean existsGameBySource(String source);

}

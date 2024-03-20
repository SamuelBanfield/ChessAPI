package com.sam.chess.db.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Repository for games.
 */
@Component
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    
}

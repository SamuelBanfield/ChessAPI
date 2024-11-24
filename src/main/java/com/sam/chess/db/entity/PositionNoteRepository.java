package com.sam.chess.db.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Repository for moves.
 */
@Component
public interface PositionNoteRepository extends JpaRepository<PositionNoteEntity, Long> {
  
  Optional<PositionNoteEntity> findOneByfen(String fen);

}

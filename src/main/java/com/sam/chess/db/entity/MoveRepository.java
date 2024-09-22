package com.sam.chess.db.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Repository for moves.
 */
@Component
public interface MoveRepository extends JpaRepository<MoveEntity, Long> {
  
  Optional<MoveEntity> findOneByStartAndEnd(String start, String end);

  List<MoveEntity> findAllByStartLike(String start);

}

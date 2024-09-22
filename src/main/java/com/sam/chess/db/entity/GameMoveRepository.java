package com.sam.chess.db.entity;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import com.sam.chess.model.Colour;
import com.sam.chess.model.Source;

import jakarta.persistence.criteria.Predicate;

/**
 * Repository for moves.
 */
@Component
public interface GameMoveRepository extends JpaSpecificationExecutor<GameMoveEntity>, JpaRepository<GameMoveEntity, Long> {

  public static Specification<GameMoveEntity> matchesPosition(final String positionRegex) {
    return (root, query, cb) -> cb.like(root.get("_move").get("start"), positionRegex);
  }

  /**
   * @return Matches any of these sources for this colour.
   */
  public static Specification<GameMoveEntity> matchesSource(final List<Source> sources, final Colour colour) {
    return (root, query, cb) -> 
      cb.or(sources.stream().map(source -> matchesSource(source, colour)).toArray(Predicate[]::new));
  }

  /**
   * @return Matches this source for this colour.
   */
  public static Specification<GameMoveEntity> matchesSource(final Source source, final Colour colour) {
    return (root, query, cb) -> cb.and(
      cb.equal(root.get("_move").get("_site"), source.site()),
      cb.equal(root.get("_game").get(colour == Colour.WHITE ? "_whitePlayer" : "_blackPlayer"), source.user())
    );
  }

}

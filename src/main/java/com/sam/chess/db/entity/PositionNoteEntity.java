package com.sam.chess.db.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.sam.chess.model.PositionNote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "position_note")
public class PositionNoteEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long _id;

  @Column(name = "note")
  private String _note;
  
  // Underscore missing for JPARepository compatibility
  @Column(name = "position")
  private String fen;

  public static PositionNoteEntity create(final String note, final String fen) {
    PositionNoteEntity entity = new PositionNoteEntity();
    entity._note = note;
    entity.fen = fen;
    return entity;
  }

  public PositionNote note() {
    return new PositionNote(_note);
  }

  public PositionNoteEntity withNote(final String note) {
    _note = note;
    return this;
  }

}

package com.sam.chess.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.sam.chess.db.entity.PositionNoteRepository;

/**
 * Requires an empty db.
 */
@SpringBootTest
public class TestNotesController {

  @Autowired
  private NotesController _notesController;

  @Autowired
  private PositionNoteRepository _repo;

  @Test
  void testInvalidFEN() {
    assertThrows(IllegalArgumentException.class, () -> _notesController.getNote("not a fen"));
    assertThrows(IllegalArgumentException.class, () -> _notesController.upsertNote("not a fen", "note"));
  }

  @Test
  void testCreateNote() {
    final String fen = "r1bqkb1r/ppp2ppp/2np1n2/4p3/2B1P3/2NP1N2/PPP2PPP/R1BQK2R b KQkq - 0 5";
    assertEquals(null, _notesController.getNote(fen));
    _notesController.upsertNote(fen, "Note");
    assertEquals("Note", _notesController.getNote(fen));
  }

  @Test
  void testUpdateNote() {
    final String fen = "rnbqk2r/pppp1ppp/4pn2/8/1bP5/2N2N2/PPQPPPPP/R1B1KB1R b KQkq - 5 4";
    _notesController.upsertNote(fen, "Note");
    assertEquals(Integer.toString(_repo.findAll().size()), "Note", _notesController.getNote(fen));
    _notesController.upsertNote(fen, "New Note");
    assertEquals("New Note", _notesController.getNote(fen));
    assertEquals("New Note", _notesController.getNote("rnbqk2r/pppp1ppp/4pn2/8/1bP5/2N2N2/PPQPPPPP/R1B1KB1R b KQkq c6 5 4"));

  }

}

package com.sam.chess.controller;

import static com.sam.chess.controller.PositionController.GAME_PATTERN;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sam.chess.db.entity.PositionNoteEntity;
import com.sam.chess.db.entity.PositionNoteRepository;
import com.sam.chess.model.PositionNote;

@RestController
@RequestMapping("/note")
public class NotesController {

	@Autowired
	private PositionNoteRepository _positionNoteRepository;

	@RequestMapping(path = "", method = GET)
	public PositionNote getNote(
		@RequestParam("fen") final String position
	) {
		System.out.println("Fetching note for " + position);
		Optional<PositionNoteEntity> note = _positionNoteRepository.findOneByfen(parseFen(position));
	
    return note.map(PositionNoteEntity::note).orElse(PositionNote.EMPTY);
	}

	@RequestMapping(path = "", method = POST)
  @ResponseBody
  public PositionNote upsertNote(	
		@RequestParam("fen") final String position,
		@RequestParam("note") final String note
	) {
		System.out.println("Setting note for position " + position);
		final String fen = parseFen(position);
		final PositionNoteEntity noteEntity = _positionNoteRepository.findOneByfen(fen)
			.map(current -> current.withNote(note))
			.orElseGet(() -> PositionNoteEntity.create(note, fen));
		return _positionNoteRepository.save(noteEntity).note();
	}

	private static String parseFen(final String rawPosition) {
		final Matcher matcher = GAME_PATTERN.matcher(rawPosition);
    if (!matcher.find()) {
      throw new IllegalArgumentException("Invalid FEN: " + rawPosition);
    }
		return matcher.group();
	}
}

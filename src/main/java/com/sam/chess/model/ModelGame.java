package com.sam.chess.model;

import java.util.List;

import com.sam.chess.model.Source.Site;

/**
 * A game of chess.
 */
public record ModelGame(String whitePlayer, String blackPlayer, GameResult result, List<ModelMove> moves, String source, Site site) {
}

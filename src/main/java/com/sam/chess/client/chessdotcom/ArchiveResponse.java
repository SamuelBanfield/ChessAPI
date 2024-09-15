package com.sam.chess.client.chessdotcom;

import java.util.List;

public record ArchiveResponse(List<GameResponse> games) {

    public record GameResponse(PlayerResponse white, PlayerResponse black, String pgn) {

        public record PlayerResponse(String username, int rating) {
        }
    }
}

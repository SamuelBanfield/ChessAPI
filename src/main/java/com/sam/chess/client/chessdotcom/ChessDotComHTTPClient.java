package com.sam.chess.client.chessdotcom;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChessDotComHTTPClient {

    private final HttpClient _client = HttpClient.newHttpClient();

    @Autowired
    public ChessDotComHTTPClient() {}

    public HttpResponse<String> getArchivesAvailable(final String userId) throws IOException, InterruptedException {
        return get(archivesAvailableURL(userId));
    }

    public HttpResponse<String> getGamesFromArchive(final String archiveURL) throws IOException, InterruptedException {
        return get(archiveURL);
    }

    private HttpResponse<String> get(final String url) throws IOException, InterruptedException {
        return _client.send(
            HttpRequest.newBuilder().GET()
                .uri(URI.create(url))
                .header("User-Agent", "Java Chess API / 1.0")
                .build(),
            HttpResponse.BodyHandlers.ofString()
        );
    }

    private static String archivesAvailableURL(final String userId) {
        // TODO: make this safer
        return "https://api.chess.com/pub/player/" + userId + "/games/archives";
    }

}
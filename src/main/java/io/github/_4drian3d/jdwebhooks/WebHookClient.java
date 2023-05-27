package io.github._4drian3d.jdwebhooks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("unused")
public final class WebHookClient {
    private static final String BASE_URL = "https://discord.com/api/webhooks/%s/%s";
    private final URI webhookURL;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new DateSerializer())
            .create();

    private WebHookClient(final URI url) {
        this.webhookURL = url;
    }

    public static WebHookClient fromURL(final @NotNull String uri) {
        requireNonNull(uri, "uri");
        try {
            final URI webhookURI = new URI(uri);
            return new WebHookClient(webhookURI);
        } catch (final URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URI provided", e);
        }
    }

    public static WebHookClient from(final @NotNull String id, final @NotNull String token) {
        requireNonNull(id, "id");
        requireNonNull(token, "token");

        return fromURL(BASE_URL.formatted(id, token));
    }

    public CompletableFuture<HttpResponse<String>> sendWebHook(final @NotNull WebHook webHook) {
        requireNonNull(webHook, "webhook");
        final String json = gson.toJson(webHook);

        return sendRequest(json);
    }

    private CompletableFuture<HttpResponse<String>> sendRequest(final String json) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(this.webhookURL)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
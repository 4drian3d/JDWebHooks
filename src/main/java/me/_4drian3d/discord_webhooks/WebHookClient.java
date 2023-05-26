package me._4drian3d.discord_webhooks;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class WebHookClient {
    private final URI webhookURL;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    private WebHookClient(final URI url) {
        this.webhookURL = url;
    }

    public static WebHookClient fromURL(final @NotNull String uri) {
        requireNonNull(uri);
        try {
            final URI webhookURI = new URI(uri);
            return new WebHookClient(webhookURI);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<HttpResponse<String>> sendMessage(final @NotNull String string) {
        final String content = "{\"content\": \""+string+"\"}";

        return sendRequest(content);
    }

    public CompletableFuture<HttpResponse<String>> sendEmbed(final @NotNull Embed embed) {
        requireNonNull(embed);

        final String json = gson.toJson(embed);

        return sendRequest(json);
    }

    private CompletableFuture<HttpResponse<String>> sendRequest(final String json) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(this.webhookURL)
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
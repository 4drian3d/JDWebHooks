package io.github._4drian3d.jdwebhooks.webhook;

import com.google.gson.Gson;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

import static java.util.Objects.*;

/**
 * An object capable of sending requests to publish WebHooks in a given Discord channel.
 */
@SuppressWarnings("unused")
record WebHookClientImpl(String webhookURL, String userAgent, Gson gson, HttpClient httpClient) implements WebHookClient {
  static final String BASE_URL = "https://discord.com/api/webhooks/%s/%s";
  static final String DEFAULT_AGENT = "github/4drian3d/JDWebhooks";

  WebHookClientImpl(String webhookURL, String userAgent) {
    this(webhookURL, userAgent, GsonProvider.provide(), HttpClient.newHttpClient());
  }

  @Override
  public CompletableFuture<HttpResponse<String>> sendWebHook(final @NonNull WebHook webHook) {
    requireNonNull(webHook, "webhook");
    final String json = gson.toJson(webHook);

    return sendRequest(json, webHook.queryParameters());
  }

  private CompletableFuture<HttpResponse<String>> sendRequest(final String json, QueryParameters queryParameters) {
    final HttpRequest request = HttpRequest.newBuilder()
        .uri(encodeURL(queryParameters))
        .header("User-Agent", this.userAgent)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
        .build();

    return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
  }

  private URI encodeURL(final @Nullable QueryParameters queryParameters) {
    if (queryParameters != null && (queryParameters.waitForMessage() != null || queryParameters.threadId() != null)) {
      final StringBuilder queryBuilder = new StringBuilder("?");
      if (queryParameters.waitForMessage() != null) {
        queryBuilder.append('=').append(queryParameters.waitForMessage());
      }
      if (queryParameters.threadId() != null) {
        if (queryParameters.waitForMessage() != null) {
          queryBuilder.append('&');
        }
        queryBuilder.append('=').append(queryParameters.threadId());
      }
      return URI.create(BASE_URL + queryBuilder);
    }
    return URI.create(BASE_URL);
  }


  static final class Builder implements WebHookClient.Builder {
    private String uri;
    private String agent;

    Builder() {
    }

    @Override
    public Builder uri(final @NonNull String uri) {
      requireNonNull(uri, "uri");
      try {
        new URI(uri);
        this.uri = uri;
      } catch (Exception e) {
        throw new IllegalArgumentException("Invalid URI provided", e);
      }
      return this;
    }

    @Override
    public Builder credentials(final @NonNull String id, final @NonNull String token) {
      requireNonNull(id, "id");
      requireNonNull(token, "token");
      this.uri(BASE_URL.formatted(id, token));
      return this;
    }

    @Override
    public Builder agent(final @NonNull String agent) {
      requireNonNull(agent, "agent");
      this.agent = agent;
      return this;
    }

    @Override
    public WebHookClientImpl build() {
      requireNonNull(uri, "uri");
      requireNonNull(agent, "agent");
      return new WebHookClientImpl(uri, agent);
    }
  }
}
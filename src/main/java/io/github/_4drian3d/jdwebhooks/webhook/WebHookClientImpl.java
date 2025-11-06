package io.github._4drian3d.jdwebhooks.webhook;

import com.google.gson.Gson;
import io.github._4drian3d.jdwebhooks.media.FileAttachment;
import io.github._4drian3d.jdwebhooks.http.HTTPMultiPartBody;
import io.github._4drian3d.jdwebhooks.http.MultiPartRecord;
import io.github._4drian3d.jdwebhooks.property.QueryParameters;
import io.github._4drian3d.jdwebhooks.serializer.GsonProvider;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.*;

import static java.util.Objects.*;

/**
 * An object capable of sending requests to publish WebHooks in a given Discord channel.
 */
@SuppressWarnings("unused")
record WebHookClientImpl(String webhookURL, String userAgent, Gson gson, HttpClient httpClient) implements WebHookClient {
  static final String BASE_URL = "https://discord.com/api/webhooks/%s/%s?with_components=true";
  static final String DEFAULT_AGENT = "github/4drian3d/JDWebhooks";

  WebHookClientImpl(String webhookURL, String userAgent) {
    this(webhookURL, userAgent, GsonProvider.provide(), HttpClient.newBuilder()
        .executor(Executors.newVirtualThreadPerTaskExecutor()).build());
  }

  @NonNull
  @Override
  public CompletableFuture<HttpResponse<String>> sendWebHook(final @NonNull WebHook webHook) {
    requireNonNull(webHook, "webhook");

    final String json = gson.toJson(webHook);

    final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
        .uri(encodeURL(webHook.queryParameters()))
        .header("User-Agent", this.userAgent);

    final List<FileAttachment> attachments = webHook.fileAttachments();
    if (attachments != null && !attachments.isEmpty()) {
      final HTTPMultiPartBody.Builder builder = HTTPMultiPartBody.builder()
          .addPart("payload_json", new MultiPartRecord.Content.StringContent(json));
      for (int i = 0; i < attachments.size(); i++) {
        final FileAttachment fileAttachment = attachments.get(i);
        builder.addPart("files[" + i + "]", fileAttachment.filename(), new MultiPartRecord.Content.FileContent(fileAttachment.file()));
      }
      final HTTPMultiPartBody multiPartBody = builder.build();
      requestBuilder.header("Content-Type", multiPartBody.contentType())
        .POST(HttpRequest.BodyPublishers.ofByteArray(multiPartBody.bytes()));
    } else {
      requestBuilder.header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8));
    }

    return this.httpClient.sendAsync(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
  }

  private URI encodeURL(final @Nullable QueryParameters queryParameters) {
    final StringBuilder queryBuilder = new StringBuilder();
    if (queryParameters != null && (queryParameters.waitForMessage() != null || queryParameters.threadId() != null)) {
      if (queryParameters.waitForMessage() != null) {
        queryBuilder.append("&wait=").append(queryParameters.waitForMessage());
      }
      if (queryParameters.threadId() != null) {
        queryBuilder.append("&thread_id=").append(queryParameters.threadId());
      }
      return URI.create(webhookURL + queryBuilder);
    }
    return URI.create(webhookURL);
  }


  static final class Builder implements WebHookClient.Builder {
    private String uri;
    private String agent;

    @Override
    @NonNull
    public Builder uri(@NonNull String uri) {
      requireNonNull(uri, "uri");
      if (!uri.contains("?with_components=true")) {
        uri += "?with_components=true";
      }
      try {
        new URI(uri);
        this.uri = uri;
      } catch (Exception e) {
        throw new IllegalArgumentException("Invalid URI provided", e);
      }
      return this;
    }

    @Override
    @NonNull
    public Builder credentials(final @NonNull String id, final @NonNull String token) {
      requireNonNull(id, "id");
      requireNonNull(token, "token");
      this.uri(BASE_URL.formatted(id, token));
      return this;
    }

    @Override
    @NonNull
    public Builder agent(final @NonNull String agent) {
      requireNonNull(agent, "agent");
      this.agent = agent;
      return this;
    }

    @Override
    @NonNull
    public WebHookClientImpl build() {
      requireNonNull(uri, "uri");
      requireNonNull(agent, "agent");
      return new WebHookClientImpl(uri, agent);
    }
  }
}
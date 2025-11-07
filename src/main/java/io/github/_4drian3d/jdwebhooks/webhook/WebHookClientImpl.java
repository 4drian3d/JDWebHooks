package io.github._4drian3d.jdwebhooks.webhook;

import com.google.gson.Gson;
import io.github._4drian3d.jdwebhooks.media.FileAttachment;
import io.github._4drian3d.jdwebhooks.http.HTTPMultiPartBody;
import io.github._4drian3d.jdwebhooks.http.MultiPartRecord;
import io.github._4drian3d.jdwebhooks.json.serializer.GsonProvider;
import org.jspecify.annotations.NonNull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static java.util.Objects.*;

/**
 * An object capable of sending requests to publish WebHooks in a given Discord channel.
 */
@SuppressWarnings("unused")
record WebHookClientImpl(
    String webhookID,
    String webHookToken,
    String userAgent,
    Gson gson,
    HttpClient httpClient
) implements WebHookClient {
  static final String DEFAULT_AGENT = "github/4drian3d/JDWebhooks";

  WebHookClientImpl(String webhookID, String webHookToken, String userAgent) {
    this(webhookID, webHookToken, userAgent, GsonProvider.provide(), HttpClient.newBuilder()
        .executor(Executors.newVirtualThreadPerTaskExecutor()).build());
  }

  @NonNull
  @Override
  public CompletableFuture<HttpResponse<String>> executeWebHook(final @NonNull WebHookExecution webHook) {
    requireNonNull(webHook, "webhook");

    final String json = gson.toJson(webHook);

    final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
        .uri(((WebHookExecutionImpl) webHook).encodeURL(this))
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

    return this.httpClient.sendAsync(requestBuilder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
  }

  @NonNull
  @Override
  public CompletableFuture<WebHookData> getWebHookData() {
    final String webHookURL = "https://discord.com/api/webhooks/%s/%s".formatted(webhookID, webHookToken);
    final HttpRequest request = HttpRequest.newBuilder(URI.create(webHookURL))
        .header("User-Agent", this.userAgent)
        .GET()
        .build();
    return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
        .thenApply(response -> gson.fromJson(response.body(), WebHookData.class));
  }

  @NonNull
  @Override
  public CompletableFuture<HttpResponse<String>> executeWebHook(@NonNull Consumer<WebHookExecution.Builder> builderConsumer) {
    final WebHookExecution.Builder builder = new WebHookExecutionImpl.Builder();
    builderConsumer.accept(builder);
    return this.executeWebHook(builder.build());
  }


  static final class Builder implements WebHookClient.Builder {
    private String webHookID;
    private String webHookToken;
    private String agent;

    @Override
    @NonNull
    public Builder credentials(final @NonNull String id, final @NonNull String token) {
      requireNonNull(id, "id");
      requireNonNull(token, "token");
      this.webHookID = id;
      this.webHookToken = token;
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
      requireNonNull(webHookID, "WebHookExecution ID");
      requireNonNull(webHookToken, "WebHookExecution Token");
      requireNonNull(agent, "agent");
      return new WebHookClientImpl(webHookID, webHookToken, agent);
    }
  }
}
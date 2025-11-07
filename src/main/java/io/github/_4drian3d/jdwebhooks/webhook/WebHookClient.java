package io.github._4drian3d.jdwebhooks.webhook;

import org.jspecify.annotations.NullMarked;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github._4drian3d.jdwebhooks.webhook.WebHookClientImpl.DEFAULT_AGENT;
import static java.util.Objects.requireNonNull;

@NullMarked
public sealed interface WebHookClient permits WebHookClientImpl {
  /**
   * Sends the specified WebHookExecution.
   *
   * @param webHook the webhook to send
   * @return a CompletableFuture with the result of this request
   */
  CompletableFuture<HttpResponse<String>> executeWebHook(final WebHookExecution webHook);

  /**
   * Sends the specified WebHookExecution.
   *
   * @param builderConsumer the webhook builder consumer used to create a new webhook execution to send
   * @return a CompletableFuture with the result of this request
   */
  CompletableFuture<HttpResponse<String>> executeWebHook(final Consumer<WebHookExecution.Builder> builderConsumer);

  /**
   * Creates a new WebHookClientImpl based on a {@link URI}.
   *
   * @param uri the webhook url provided
   * @return a new WebHookClientImpl
   * @throws IllegalArgumentException if the provided URL is invalid
   */
  static WebHookClient fromURL(final String uri) {
    return WebHookClient.fromURL(uri, DEFAULT_AGENT);
  }

  /**
   * Creates a new WebHookClient based on a {@link URI}.
   *
   * @param uri the webhook url provided
   * @param agent the http agent to use
   * @return a new WebHookClient
   * @throws IllegalArgumentException if the provided URL is invalid
   */
  static WebHookClient fromURL(final String uri, final String agent) {
    requireNonNull(uri);
    requireNonNull(agent);
    final Pattern webhookURLPattern = Pattern.compile("^https:\\\\/\\\\/discord\\\\.com\\\\/api\\\\/webhooks\\\\/(\\\\d{17,20})\\\\/([a-zA-Z0-9_-]+)$");
    final Matcher webhookURLMatcher = webhookURLPattern.matcher(uri);
    if (webhookURLMatcher.matches()) {
      final String webhookId = webhookURLMatcher.group(1);
      final String webhookToken = webhookURLMatcher.group(2);
      return builder().credentials(webhookId, webhookToken).agent(agent).build();
    } else {
      throw new IllegalArgumentException("Invalid URL provided: " + uri);
    }
  }

  /**
   * Creates a new WebHookClientImpl based on its id and token.
   *
   * @param id    the webhook id
   * @param token the webhook token
   * @return a new WebHookClientImpl
   * @throws IllegalArgumentException if the provided id or token are is invalid
   * @see #fromURL(String)
   */
  static WebHookClient fromCredentials(final String id, final String token) {
    return builder().credentials(id, token).agent(DEFAULT_AGENT).build();
  }

  /**
   * Get a builder for the creation of a new WebHookClient.
   * <br>It is recommended to use a builder instead of the static factory
   * to be able to configure a user agent corresponding to your application.
   *
   * @return a new WebHookClient Builder
   */
  static Builder builder() {
    return new WebHookClientImpl.Builder();
  }

  /**
   * Builder for the creation of a new WebHookClientImpl.
   */
  sealed interface Builder permits WebHookClientImpl.Builder {

    /**
     * Set credentials for sending webhooks
     *
     * @param id    the webhook id
     * @param token the webhook token
     * @return this builder
     * @throws IllegalArgumentException if the provided id or token are is invalid
     */
    Builder credentials(final String id, final String token);

    /**
     * Sets the user agent to use
     *
     * @param agent the user agent
     * @return this builder
     */
    Builder agent(final String agent);

    /**
     * Creates a new WebHookClientImpl based on the configuration of this builder
     *
     * @return a new WebHookClientImpl
     * @throws NullPointerException in case no credentials/uri or user agent have been configured
     */
    WebHookClient build();
  }
}

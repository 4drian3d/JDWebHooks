package io.github._4drian3d.jdwebhooks.webhook;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static io.github._4drian3d.jdwebhooks.webhook.WebHookClientImpl.DEFAULT_AGENT;

public interface WebHookClient {
  /**
   * Sends the specified WebHookImpl.
   *
   * @param webHook the webhook to send
   * @return a CompletableFuture with the result of this request
   */
  CompletableFuture<HttpResponse<String>> sendWebHook(final @NotNull WebHook webHook);

  /**
   * Creates a new WebHookClientImpl based on a {@link URI}.
   *
   * @param uri the webhook url provided
   * @return a new WebHookClientImpl
   * @throws IllegalArgumentException if the provided URL is invalid
   */
  static WebHookClient fromURL(final @NotNull String uri) {
    return builder().uri(uri).agent(DEFAULT_AGENT).build();
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
  static WebHookClient from(final @NotNull String id, final @NotNull String token) {
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
  interface Builder {
    /**
     * Sets the client URI
     *
     * @param uri the client uri
     * @return this builder
     * @throws IllegalArgumentException if the provided URL is invalid
     */
    Builder uri(final @NonNull String uri);

    /**
     * Set credentials for sending webhooks
     *
     * @param id    the webhook id
     * @param token the webhook token
     * @return this builder
     * @throws IllegalArgumentException if the provided id or token are is invalid
     */
    Builder credentials(final @NotNull String id, final @NotNull String token);

    /**
     * Sets the user agent to use
     *
     * @param agent the user agent
     * @return this builder
     */
    Builder agent(final @NotNull String agent);

    /**
     * Creates a new WebHookClientImpl based on the configuration of this builder
     *
     * @return a new WebHookClientImpl
     * @throws NullPointerException in case no credentials/uri or user agent have been configured
     */
    WebHookClient build();
  }
}

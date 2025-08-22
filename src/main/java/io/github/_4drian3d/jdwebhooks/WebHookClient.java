package io.github._4drian3d.jdwebhooks;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.component.*;
import io.github._4drian3d.jdwebhooks.serializer.*;
import org.jetbrains.annotations.*;

import java.net.*;
import java.net.http.*;
import java.nio.charset.*;
import java.time.*;
import java.util.concurrent.*;

import static java.util.Objects.*;

/**
 * An object capable of sending requests to publish WebHooks in a given Discord channel.
 */
@SuppressWarnings("unused")
public final class WebHookClient {
    private static final String BASE_URL = "https://discord.com/api/webhooks/%s/%s";
    private static final String DEFAULT_AGENT = "github/4drian3d/JDWebhooks";
    private final URI webhookURL;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String userAgent;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new DateSerializer())
            .registerTypeAdapter(Embed.class, new EmbedSerializer())
            .registerTypeAdapter(WebHook.class, new WebHookSerializer())
            .registerTypeAdapter(AllowedMentions.class, new AllowedMentionsSerializer())
            .registerTypeAdapter(TextDisplayComponent.class, new ComponentSerializer())
            .create();

    private WebHookClient(final Builder builder) {
        this.webhookURL = builder.uri;
        this.userAgent = builder.agent;
    }

    /**
     * Get a builder for the creation of a new WebHookClient.
     * <br>It is recommended to use a builder instead of the static factory
     * to be able to configure a user agent corresponding to your application.
     *
     * @return a new WebHookClient Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new WebHookClient based on a {@link URI}.
     *
     * @param uri the webhook url provided
     * @return a new WebHookClient
     * @throws IllegalArgumentException if the provided URL is invalid
     */
    public static WebHookClient fromURL(final @NotNull String uri) {
        return builder().uri(uri).agent(DEFAULT_AGENT).build();
    }

    /**
     * Creates a new WebHookClient based on its id and token.
     *
     * @param id    the webhook id
     * @param token the webhook token
     * @return a new WebHookClient
     * @throws IllegalArgumentException if the provided id or token are is invalid
     * @see #fromURL(String)
     */
    public static WebHookClient from(final @NotNull String id, final @NotNull String token) {
        return builder().credentials(id, token).agent(DEFAULT_AGENT).build();
    }

    /**
     * Sends the specified WebHook.
     *
     * @param webHook the webhook to send
     * @return a CompletableFuture with the result of this request
     */
    public CompletableFuture<HttpResponse<String>> sendWebHook(final @NotNull WebHook webHook) {
        requireNonNull(webHook, "webhook");
        final String json = gson.toJson(webHook);

        var webHookURL = this.webhookURL;

        // check for waitForMessage
        if (webHook.waitForMessage() != null && webHook.waitForMessage()) {
            webHookURL = withQueryParam(
                    this.webhookURL,
                    "wait",
                    "true"
            );
        }

        // check for threadId
        if (webHook.threadId() != null && !webHook.threadId().isEmpty()) {
            webHookURL = withQueryParam(
                    this.webhookURL,
                    "thread_id",
                    webHook.threadId()
            );
        }

        // if we have components add with_components
        if (webHook.components() != null && !webHook.components().isEmpty()) {
            webHookURL = withQueryParam(
                    webHookURL,
                    "with_components",
                    "true"
            );
        }

        return sendRequest(json, webHookURL);
    }

    private CompletableFuture<HttpResponse<String>> sendRequest(final String json, final URI webhookURL) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(webhookURL)
                .header("User-Agent", this.userAgent)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Utility function that adds a query parameter to the given base URI.
     *
     * @param base  the base URI to which the query parameter should be added
     * @param key   the query parameter key
     * @param value the query parameter value
     * @return a new URI with the query parameter added
     */
    private URI withQueryParam(URI base, String key, String value) {
        try {
            String existingQuery = base.getQuery();
            String newQuery = (existingQuery == null ? "" : existingQuery + "&")
                    + URLEncoder.encode(key, StandardCharsets.UTF_8)
                    + "="
                    + URLEncoder.encode(value, StandardCharsets.UTF_8);

            return new URI(
                    base.getScheme(),
                    base.getAuthority(),
                    base.getPath(),
                    newQuery,
                    base.getFragment()
            );
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Failed to build URI with query parameter", e);
        }
    }

    /**
     * Builder for the creation of a new WebHookClient.
     */
    public static final class Builder {
        private URI uri;
        private String agent;

        private Builder() {
        }

        /**
         * Sets the client URI
         *
         * @param uri the client uri
         * @return this builder
         * @throws IllegalArgumentException if the provided URL is invalid
         */
        public Builder uri(final @NotNull String uri) {
            requireNonNull(uri, "uri");
            try {
                this.uri = new URI(uri);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid URI provided", e);
            }
            return this;
        }

        /**
         * Set credentials for sending webhooks
         *
         * @param id the webhook id
         * @param token the webhook token
         * @return this builder
         * @throws IllegalArgumentException if the provided id or token are is invalid
         */
        public Builder credentials(final @NotNull String id, final @NotNull String token) {
            requireNonNull(id, "id");
            requireNonNull(token, "token");
            this.uri(BASE_URL.formatted(id, token));
            return this;
        }

        /**
         * Sets the user agent to use
         *
         * @param agent the user agent
         * @return this builder
         */
        public Builder agent(final @NotNull String agent) {
            requireNonNull(agent, "agent");
            this.agent = agent;
            return this;
        }

        /**
         * Creates a new WebHookClient based on the configuration of this builder
         *
         * @return a new WebHookClient
         * @throws NullPointerException in case no credentials/uri or user agent have been configured
         */
        public WebHookClient build() {
            requireNonNull(uri, "uri");
            requireNonNull(agent, "agent");
            return new WebHookClient(this);
        }
    }
}
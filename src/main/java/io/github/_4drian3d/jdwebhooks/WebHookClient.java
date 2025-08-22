package io.github._4drian3d.jdwebhooks;

import com.google.gson.*;
import okhttp3.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
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
    private final OkHttpClient httpClient = new OkHttpClient();
    private final String userAgent;
    private final Gson gson = GsonProvider.getGson();

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
    public CompletableFuture<Response> sendWebHook(final @NotNull WebHook webHook) {
        requireNonNull(webHook, "webhook");

        final var urlBuilder = requireNonNull(HttpUrl.parse(this.webhookURL.toString())).newBuilder();

        // check for waitForMessage
        if (webHook.waitForMessage() != null && webHook.waitForMessage()) {
            urlBuilder.addQueryParameter("wait", "true");
        }

        // check for threadId
        if (webHook.threadId() != null && !webHook.threadId().isEmpty()) {
            urlBuilder.addQueryParameter("thread_id", webHook.threadId());
        }

        // if we have components add with_components
        if (webHook.components() != null && !webHook.components().isEmpty()) {
            urlBuilder.addQueryParameter("with_components", "true");
        }

        // build the request body
        final RequestBody body;
        try {
            body = getBody(webHook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final var request = new Request.Builder()
                .url(urlBuilder.build())
                .post(body)
                .addHeader("User-Agent", this.userAgent)
                .build();

        final var future = new CompletableFuture<Response>();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                future.complete(response);
            }
        });

        return future;
    }

    private RequestBody getBody(final @NotNull WebHook webHook) throws IOException {
        if (webHook.attachments() == null || webHook.attachments().isEmpty()) {
            final String json = gson.toJson(webHook);
            return RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        } else {
            final var multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            // add payload_json part
            final String json = gson.toJson(webHook);
            final var jsonBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
            multipartBuilder.addFormDataPart("payload_json", null, jsonBody);

            // add files parts
            for (int i = 0; i < webHook.attachments().size(); i++) {
                final FileAttachment attachment = webHook.attachments().get(i);

                final var file = attachment.file();
                final var fileType = Files.probeContentType(file.toPath());

                final RequestBody fileBody = RequestBody.create(attachment.file(), fileType != null ? MediaType.get(fileType) : MediaType.get("application/octet-stream"));
                multipartBuilder.addFormDataPart("files[" + i + "]", attachment.getFilename(), fileBody);
            }

            return multipartBuilder.build();
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
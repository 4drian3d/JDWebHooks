package io.github._4drian3d.jdwebhooks;

import org.jetbrains.annotations.*;

import java.util.*;

import static java.util.Objects.*;

/**
 * Object containing all available items to display in a Discord WebHook.
 *
 * @param content the message content of this webhook
 * @param username the username to overwrite the webhook name
 * @param avatarURL the avatar icon url
 * @param tts
 * @param embeds the embeds to attach to this webhook
 * @param allowedMentions if this webhook can mention users
 * @param threadName the thread to be created from this webhook
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public record WebHook(
        @NotNull String content,
        @Nullable String username,
        @Nullable String avatarURL,
        @Nullable Boolean tts,
        @Nullable List<Embed> embeds,
        @Nullable AllowedMentions allowedMentions,
        @Nullable String threadName,
        @Nullable String threadId,
        @Nullable Boolean waitForMessage
) {
    public WebHook {
        requireNonNull(content, "content");
    }

    /**
     * Creates a new WebHook Builder.
     *
     * @return a new WebHook builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * WebHook Builder
     */
    public static final class Builder {
        private String content = "";
        private String username;
        private String avatarURL;
        private Boolean tts;
        private List<Embed> embeds;
        private AllowedMentions allowedMentions;
        private String threadName;
        private String threadId;
        private Boolean waitForMessage;

        private Builder() {
        }

        /**
         * Sets the content string of this embed.
         * <p>It can be an empty string and in case no content is provided,
         * an empty string will be used</p>
         *
         * @param content the content string
         * @return this builder
         */
        public Builder content(final @NotNull String content) {
            this.content = requireNonNull(content, "content");
            return this;
        }

        /**
         * Sets the override username of this WebHook.
         *
         * @param username the override username
         * @return this builder
         */
        public Builder username(final @Nullable String username) {
            this.username = requireNonNull(username);
            return this;
        }

        /**
         * Sets the Avatar URL of this WebHook
         *
         * @param avatarURL the Avatar URL
         * @return this builder
         */
        public Builder avatarURL(final @Nullable String avatarURL) {
            this.avatarURL = avatarURL;
            return this;
        }

        public Builder tts(final @Nullable Boolean tts) {
            this.tts = tts;
            return this;
        }

        public Builder embed(final @NotNull Embed embed) {
            requireNonNull(embed);
            if (this.embeds == null) {
                this.embeds = new ArrayList<>();
            }
            this.embeds.add(embed);
            return this;
        }

        public Builder embeds(final @NotNull List<@NotNull Embed> embeds) {
            requireNonNull(embeds);
            for (final Embed embed : embeds) {
                this.embed(embed);
            }
            return this;
        }

        public Builder embeds(@NotNull Embed@NotNull... embeds) {
            requireNonNull(embeds);
            for (final Embed embed : embeds) {
                this.embed(embed);
            }
            return this;
        }

        public Builder allowedMentions(final @Nullable AllowedMentions allowedMentions) {
            this.allowedMentions = allowedMentions;
            return this;
        }

        public Builder threadName(final @Nullable String threadName) {
            this.threadName = threadName;
            return this;
        }

        /**
         * Sets the ID of an existing thread to send the message in.
         *
         * @param threadId The "thread_id" query parameter
         * @return this builder
         * @see <a href=https://discord.com/developers/docs/resources/webhook#execute-webhook-query-string-params>Execute Webhook Query String Params</a>
         */
        public Builder threadId(final @Nullable String threadId) {
            this.threadId = threadId;
            return this;
        }

        /**
         * Sets whether the response should wait for the message to be created.
         *
         * @param waitForMessage the "wait" query parameter
         * @return this builder
         * @see <a href=https://discord.com/developers/docs/resources/webhook#execute-webhook-query-string-params>Execute Webhook Query String Params</a>
         */
        public Builder waitForMessage(final @Nullable Boolean waitForMessage) {
            this.waitForMessage = waitForMessage;
            return this;
        }

        public WebHook build() {
            return new WebHook(
                    this.content,
                    this.username,
                    this.avatarURL,
                    this.tts,
                    this.embeds,
                    this.allowedMentions,
                    this.threadName,
                    this.threadId,
                    this.waitForMessage
            );
        }
    }
}

package io.github._4drian3d.jdwebhooks;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public record WebHook(
        @NotNull String content,
        @Nullable String username,
        @SerializedName("avatar_url")
        @Nullable String avatarURL,
        @Nullable Boolean tts,
        @Nullable List<Embed> embeds,
        @SerializedName("allowed_mentions")
        @Nullable Boolean allowedMentions,
        @SerializedName("thread_name")
        @Nullable String threadName
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String content = "";
        private String username;
        private String avatarURL;
        private Boolean tts;
        private List<Embed> embeds;
        private Boolean allowedMentions;
        private String threadName;

        private Builder() {
        }

        public Builder content(final @NotNull String content) {
            this.content = requireNonNull(content, "content");
            return this;
        }

        public Builder username(final @Nullable String username) {
            this.username = requireNonNull(username);
            return this;
        }

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
            this.embeds = embeds;
            return this;
        }

        public Builder embeds(@NotNull Embed@NotNull... embeds) {
            requireNonNull(embeds);
            for (final Embed embed : embeds) {
                this.embed(embed);
            }
            return this;
        }

        public Builder allowedMentions(Boolean allowedMentions) {
            this.allowedMentions = allowedMentions;
            return this;
        }

        public Builder threadName(String threadName) {
            this.threadName = threadName;
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
                    this.threadName
            );
        }
    }
}

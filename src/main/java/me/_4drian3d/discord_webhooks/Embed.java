package me._4drian3d.discord_webhooks;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

public record Embed(
        @Nullable String title,
        @Nullable String type,
        @Nullable String description,
        @Nullable String url,
        @Nullable Instant timestamp,
        @Nullable Integer color,
        @Nullable Footer footer,
        @Nullable Image image,
        @Nullable Thumbnail thumbnail,
        @Nullable Video video,
        @Nullable Provider provider,
        @Nullable Author author,
        @Nullable Field[] fields
) {

    public static Builder builder() {
        return new Builder();
    }

    public record Footer(
            @Nullable String text,
            @SerializedName("icon_url")
            @Nullable String iconURL,
            @Nullable String proxyIconURL
    ) {
    }

    public record Thumbnail(
            @NotNull String url,
            @SerializedName("proxy_url")
            @Nullable String proxyURL,
            @Nullable String height,
            @Nullable String width
    ) {
    }

    public record Video(
            @NotNull String url,
            @SerializedName("proxy_url")
            @Nullable String proxyURL,
            @Nullable String height,
            @Nullable String width
    ) {
    }

    public record Image(
            @NotNull String url,
            @SerializedName("proxy_url")
            @Nullable String proxyURL,
            @Nullable String height,
            @Nullable String width
    ) {
    }

    public record Provider(
            @Nullable String name,
            @Nullable String url
    ) {
    }

    public record Author(
            @NotNull String author,
            @Nullable String url,
            @SerializedName("icon_url")
            @Nullable String iconURL,
            @SerializedName("proxy_icon_url")
            @Nullable String proxyIconURL
    ) {
    }

    public record Field(
            @Nullable Boolean inline,
            String name,
            String value
    ) {
    }

    public static class Builder {
        private String title;
        private String type;
        private String description;
        private String url;
        private Instant timestamp;
        private Integer color;
        private Footer footer;
        private Thumbnail thumbnail;
        private Video video;
        private Image image;
        private Author author;
        private Provider provider;
        private List<Field> fields;

        public Builder title(final @Nullable String title) {
            this.title = title;
            return this;
        }

        public Builder type(final @Nullable String type) {
            this.type = type;
            return this;
        }

        public Builder description(final @Nullable String description) {
            this.description = description;
            return this;
        }

        public Builder url(final @Nullable String url) {
            this.url = url;
            return this;
        }

        public Builder timestamp(final @Nullable Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder color(final @Nullable Integer color) {
            this.color = color;
            return this;
        }

        public Builder footer(final @Nullable Footer footer) {
            this.footer = footer;
            return this;
        }

        public Builder thumbnail(Thumbnail thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder video(Video video) {
            this.video = video;
            return this;
        }

        public Builder image(final @Nullable Image image) {
            this.image = image;
            return this;
        }

        public Builder author(final @Nullable Author author) {
            this.author = author;
            return this;
        }

        public Builder provider(final @Nullable Provider provider) {
            this.provider = provider;
            return this;
        }

        public Builder field(final @NotNull Field field) {
            requireNonNull(field);
            if (this.fields == null) {
                this.fields = new ArrayList<>();
            }
            fields.add(field);

            return this;
        }

        public Builder fields(final @NotNull Collection<@NotNull Field> fields) {
            requireNonNull(fields);
            for (final Field field : fields) {
                this.field(field);
            }
            return this;
        }

        @Contract("-> new")
        public @NotNull Embed build() {
            return new Embed(
                    this.title,
                    this.type,
                    this.description,
                    this.url,
                    this.timestamp,
                    this.color,
                    this.footer,
                    this.image,
                    this.thumbnail,
                    this.video,
                    this.provider,
                    this.author,
                    this.fields == null ? null : this.fields.toArray(Field[]::new)
            );
        }
    }
}

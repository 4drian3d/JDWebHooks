package io.github._4drian3d.jdwebhooks;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

@SuppressWarnings({"UnstableApiUsage", "unused", "UnusedReturnValue"})
public record Embed(
        @Nullable String title,
        @Nullable String type,
        @Nullable String description,
        @Nullable String url,
        @Nullable OffsetDateTime timestamp,
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
            @NotNull String text,
            @SerializedName("icon_url")
            @Nullable String iconURL,
            @Nullable String proxyIconURL
    ) {
        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private String text;
            private String iconURL;
            private String proxyIconURL;

            private Builder() {
            }

            public Builder text(final @NotNull String text) {
                this.text = requireNonNull(text, "text");
                return this;
            }

            public Builder iconURL(final @Nullable String iconURL) {
                this.iconURL = iconURL;
                return this;
            }

            public Builder proxyIconURL(final @Nullable String proxyIconURL) {
                this.proxyIconURL = proxyIconURL;
                return this;
            }

            public Footer build() {
                return new Footer(
                        requireNonNull(this.text),
                        this.iconURL,
                        this.proxyIconURL
                );
            }
        }
    }

    public record Thumbnail(
            @NotNull String url,
            @SerializedName("proxy_url")
            @Nullable String proxyURL,
            @Nullable String height,
            @Nullable String width
    ) {
        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private String url;
            private String proxyURL;
            private String height;
            private String width;

            private Builder() {
            }

            public Builder url(final @NotNull String url) {
                this.url = requireNonNull(url, "url");
                return this;
            }

            public Builder proxyURL(final @Nullable String proxyURL) {
                this.proxyURL = proxyURL;
                return this;
            }

            public Builder height(final @Nullable String height) {
                this.height = height;
                return this;
            }

            public Builder width(final @Nullable String width) {
                this.width = width;
                return this;
            }

            public Thumbnail build() {
                return new Thumbnail(
                        requireNonNull(this.url),
                        this.proxyURL,
                        this.height,
                        this.width
                );
            }
        }
    }

    public record Video(
            @NotNull String url,
            @SerializedName("proxy_url")
            @Nullable String proxyURL,
            @Nullable String height,
            @Nullable String width
    ) {
        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private String url;
            private String proxyURL;
            private String height;
            private String width;

            private Builder() {
            }

            public Builder url(final @NotNull String url) {
                this.url = requireNonNull(url, "url");
                return this;
            }

            public Builder proxyURL(final @Nullable String proxyURL) {
                this.proxyURL = proxyURL;
                return this;
            }

            public Builder height(final @Nullable String height) {
                this.height = height;
                return this;
            }

            public Builder width(final @Nullable String width) {
                this.width = width;
                return this;
            }

            public Video build() {
                return new Video(
                        requireNonNull(this.url),
                        this.proxyURL,
                        this.height,
                        this.width
                );
            }
        }
    }

    public record Image(
            @NotNull String url,
            @SerializedName("proxy_url")
            @Nullable String proxyURL,
            @Nullable String height,
            @Nullable String width
    ) {
        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private String url;
            private String proxyURL;
            private String height;
            private String width;

            private Builder() {
            }

            public Builder url(final @NotNull String url) {
                this.url = requireNonNull(url, "url");
                return this;
            }

            public Builder proxyURL(final @Nullable String proxyURL) {
                this.proxyURL = proxyURL;
                return this;
            }

            public Builder height(final @Nullable String height) {
                this.height = height;
                return this;
            }

            public Builder width(final @Nullable String width) {
                this.width = width;
                return this;
            }

            public Image build() {
                return new Image(
                        requireNonNull(this.url, "url"),
                        this.proxyURL,
                        this.height,
                        this.width
                );
            }
        }
    }

    public record Provider(
            @Nullable String name,
            @Nullable String url
    ) {
        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private String name;
            private String url;

            public Builder name(final @Nullable String name) {
                this.name = name;
                return this;
            }

            public Builder url(final @Nullable String url) {
                this.url = url;
                return this;
            }

            public Provider build() {
                return new Provider(this.name, this.url);
            }
        }
    }

    public record Author(
            @NotNull String author,
            @Nullable String url,
            @SerializedName("icon_url")
            @Nullable String iconURL,
            @SerializedName("proxy_icon_url")
            @Nullable String proxyIconURL
    ) {
        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private String author;
            private String url;
            private String iconURL;
            private String proxyIconURL;

            private Builder() {
            }

            public Builder author(final @NotNull String author) {
                this.author = requireNonNull(author, "author");
                return this;
            }

            public Builder url(final @Nullable String url) {
                this.url = url;
                return this;
            }

            public Builder iconURL(final @Nullable String iconURL) {
                this.iconURL = iconURL;
                return this;
            }

            public Builder proxyIconURL(String proxyIconURL) {
                this.proxyIconURL = proxyIconURL;
                return this;
            }

            public Author build() {
                return new Author(
                        requireNonNull(this.author, "author"),
                        this.url,
                        this.iconURL,
                        this.proxyIconURL
                );
            }
        }
    }

    public record Field(
            @Nullable Boolean inline,
            @NotNull String name,
            @NotNull String value
    ) {
        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private Boolean inline;
            private String name;
            private String value;

            private Builder() {
            }

            public Builder inline(final @Nullable Boolean inline) {
                this.inline = inline;
                return this;
            }

            public Builder name(final @NotNull String name) {
                this.name = requireNonNull(name, "name");
                return this;
            }

            public Builder value(final @NotNull String value) {
                this.value = requireNonNull(value, "value");
                return this;
            }

            public Field build() {
                return new Field(
                        this.inline,
                        requireNonNull(this.name, "name"),
                        requireNonNull(this.value, "value")
                );
            }
        }
    }

    public static final class Builder {
        private String title;
        private String type;
        private String description;
        private String url;
        private OffsetDateTime timestamp;
        private Integer color;
        private Footer footer;
        private Thumbnail thumbnail;
        private Video video;
        private Image image;
        private Author author;
        private Provider provider;
        private List<Field> fields;

        private Builder() {
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder title(final @Nullable String title) {
            this.title = title;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder type(final @Nullable String type) {
            this.type = type;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder description(final @Nullable String description) {
            this.description = description;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder url(final @Nullable String url) {
            this.url = url;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder timestamp(final @Nullable Instant timestamp) {
            this.timestamp = timestamp == null ? null : OffsetDateTime.ofInstant(timestamp, Clock.systemDefaultZone().getZone());
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder timestamp(final @Nullable TemporalAccessor timestamp) {
            this.timestamp = timestamp == null ? null : OffsetDateTime.from(timestamp);
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder timestamp(final @Nullable OffsetDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        @Contract(value = "_, _ -> this", mutates = "this")
        public Builder timestamp(final @NotNull LocalDateTime dateTime, final @NotNull ZoneOffset offset) {
            requireNonNull(dateTime, "dateTime");
            requireNonNull(offset, "offset");
            this.timestamp = OffsetDateTime.of(dateTime, offset);
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder color(final @Nullable Integer color) {
            this.color = color;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder footer(final @Nullable Footer footer) {
            this.footer = footer;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder thumbnail(final @Nullable Thumbnail thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder video(final @Nullable Video video) {
            this.video = video;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder image(final @Nullable Image image) {
            this.image = image;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder author(final @Nullable Author author) {
            this.author = author;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder provider(final @Nullable Provider provider) {
            this.provider = provider;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder field(final @NotNull Field field) {
            requireNonNull(field, "field");
            if (this.fields == null) {
                this.fields = new ArrayList<>();
            }
            fields.add(field);

            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder fields(final @NotNull Collection<@NotNull Field> fields) {
            requireNonNull(fields, "fields");
            for (final Field field : fields) {
                this.field(field);
            }
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder fields(final @NotNull Field @NotNull ... fields) {
            requireNonNull(fields, "fields");
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

package io.github._4drian3d.jdwebhooks.component;

import org.jetbrains.annotations.*;

public final class ThumbnailComponent extends Component implements AccessoryComponent {
    @NotNull
    private final String media;
    private final String description;
    private final Boolean spoiler;

    ThumbnailComponent(int id, @NotNull final String media, final String description, final Boolean spoiler) {
        super(ComponentType.THUMBNAIL, id);

        if (description != null && description.length() > 1024) {
            throw new IllegalArgumentException("Description length must be less than or equal to 1024 characters.");
        }
        this.media = media;
        this.description = description;
        this.spoiler = spoiler;
    }

    @NotNull
    public String getMedia() {
        return media;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getSpoiler() {
        return spoiler;
    }

    public static final class Builder extends Component.Builder<Builder> {
        @NotNull
        private String media;
        private String description;
        private Boolean spoiler;

        Builder() {
            super();
            this.media = "";
            this.description = null;
            this.spoiler = null;
        }

        public Builder media(@NotNull final String media) {
            this.media = media;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder spoiler(final Boolean spoiler) {
            this.spoiler = spoiler;
            return this;
        }

        @Override
        public ThumbnailComponent build() {
            return new ThumbnailComponent(id, media, description, spoiler);
        }
    }
}

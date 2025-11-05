package io.github._4drian3d.jdwebhooks.component;

import io.github._4drian3d.jdwebhooks.media.MediaReference;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("unused")
record ThumbnailComponentImpl(
    @Nullable Integer id,
    ComponentType componentType,
    MediaReference media,
    @Nullable String description,
    @Nullable Boolean spoiler
) implements ThumbnailComponent {

  ThumbnailComponentImpl(final Integer id, @NonNull final MediaReference media, final String description, final Boolean spoiler) {
    this(id, ComponentType.THUMBNAIL, media, description, spoiler);
  }

  static final class Builder extends AbstractComponentBuilder<ThumbnailComponent, ThumbnailComponent.Builder> implements ThumbnailComponent.Builder {
    private MediaReference media;
    private String description;
    private Boolean spoiler;

    @NonNull
    @Override
    public Builder media(final @NonNull MediaReference mediaReference) {
      requireNonNull(mediaReference);
      this.media = mediaReference;
      return this;
    }

    @NonNull
    @Override
    public Builder description(final String description) {
      this.description = description;
      return this;
    }

    @NonNull
    @Override
    public Builder spoiler(final Boolean spoiler) {
      this.spoiler = spoiler;
      return this;
    }

    @Override
    public ThumbnailComponentImpl build() {
      requireNonNull(media);
      if (description != null && description.length() > 1024) {
        throw new IllegalArgumentException("Description length must be less than or equal to 1024 characters.");
      }
      return new ThumbnailComponentImpl(id, media, description, spoiler);
    }
  }
}

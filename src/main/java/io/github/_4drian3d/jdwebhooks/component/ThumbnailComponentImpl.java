package io.github._4drian3d.jdwebhooks.component;

import io.github._4drian3d.jdwebhooks.webhook.FileAttachment;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.net.URI;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("unused")
record ThumbnailComponentImpl(
    @Nullable Integer id,
    ComponentType componentType,
    String media,
    @Nullable String description,
    @Nullable Boolean spoiler
) implements ThumbnailComponent {

  ThumbnailComponentImpl(final Integer id, @NonNull final String media, final String description, final Boolean spoiler) {
    this(id, ComponentType.THUMBNAIL, media, description, spoiler);
  }

  static final class Builder extends AbstractComponentBuilder<ThumbnailComponent, ThumbnailComponent.Builder> implements ThumbnailComponent.Builder {
    private String media;
    private String description;
    private Boolean spoiler;

    @NonNull
    @Override
    public Builder media(final @NonNull URI mediaURI) {
      requireNonNull(mediaURI);
      this.media = mediaURI.toString();
      return this;
    }

    @NonNull
    @Override
    public Builder media(final @NonNull FileAttachment fileAttachment) {
      requireNonNull(fileAttachment);
      this.media = fileAttachment.filename();
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

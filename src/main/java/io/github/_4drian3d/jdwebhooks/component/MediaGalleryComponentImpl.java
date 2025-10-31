package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.*;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("unused")
record MediaGalleryComponentImpl(
    @Nullable Integer id,
    ComponentType componentType,
    @NonNull List<MediaGalleryComponent.@NonNull Item> items
) implements MediaGalleryComponent {

  MediaGalleryComponentImpl(final Integer id, @NonNull final List<MediaGalleryComponent.@NonNull Item> items) {
    this(id, ComponentType.MEDIA_GALLERY, List.copyOf(items));
  }

  public static Item.Builder item(@NonNull final String media) {
    return new Item.Builder();
  }

  record Item(@NonNull String media, String description, Boolean spoiler) implements MediaGalleryComponent.Item {
    public static final class Builder implements MediaGalleryComponent.Item.Builder {
      private String media;
      private String description;
      private Boolean spoiler;

      @NonNull
      @Override
      public Builder media(@NonNull final String media) {
        this.media = media;
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
      public MediaGalleryComponent.@NonNull Item build() {
        requireNonNull(media);
        return new Item(media, description, spoiler);
      }
    }
  }

  @NullMarked
  static final class Builder extends AbstractComponentBuilder<MediaGalleryComponent, MediaGalleryComponent.Builder> implements MediaGalleryComponent.Builder {
    private final List<MediaGalleryComponent.Item> items = new ArrayList<>();

    @Override
    public Builder item(final MediaGalleryComponent.Item item) {
      this.items.add(item);
      return this;
    }

    public Builder items(final MediaGalleryComponent.Item... items) {
      this.items.clear();
      Collections.addAll(this.items, items);
      return this;
    }

    public Builder items(final List<MediaGalleryComponent.Item> items) {
      this.items.clear();
      this.items.addAll(items);
      return this;
    }

    @Override
    public MediaGalleryComponent build() {
      requireNonNull(items);
      if (items.isEmpty() || items.size() > 10) {
        throw new IllegalArgumentException("Media gallery must have between 1 and 10 items.");
      }
      return new MediaGalleryComponentImpl(id, items);
    }
  }
}

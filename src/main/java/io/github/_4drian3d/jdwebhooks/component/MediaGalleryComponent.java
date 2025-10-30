package io.github._4drian3d.jdwebhooks.component;

import org.jetbrains.annotations.*;

import java.util.*;

@SuppressWarnings("unused")
public final class MediaGalleryComponent extends Component implements ContainerableComponent {
    @NotNull
    final List<@NotNull Item> items;

    MediaGalleryComponent(final int id, @NotNull final List<@NotNull Item> items) {
        super(ComponentType.MEDIA_GALLERY, id);

        if (items.isEmpty() || items.size() > 10) {
            throw new IllegalArgumentException("Media gallery must have between 1 and 10 items.");
        }
        this.items = List.copyOf(items);
    }

    @NotNull
    public List<@NotNull Item> getItems() {
        return items;
    }

    public static Item.Builder item(@NotNull final String media) {
        return new Item.Builder(media);
    }

    public record Item(@NotNull String media, String description, Boolean spoiler) {
        public static class Builder {
            @NotNull
            private String media;
            private String description;
            private Boolean spoiler;

            Builder(@NotNull final String media) {
                this.media = media;
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

            public Item build() {
                return new Item(media, description, spoiler);
            }
        }
    }

    public static class Builder extends Component.Builder<Builder> {
        @NotNull
        private final List<@NotNull Item> items;

        Builder() {
            super();
            this.items = new ArrayList<>();
        }

        public Builder item(@NotNull final Item item) {
            this.items.add(item);
            return this;
        }

        public Builder items(@NotNull final Item... items) {
            this.items.clear();
            Collections.addAll(this.items, items);
            return this;
        }

        public Builder items(@NotNull final List<@NotNull Item> items) {
            this.items.clear();
            this.items.addAll(items);
            return this;
        }

        @Override
        public MediaGalleryComponent build() {
            return new MediaGalleryComponent(id, items);
        }
    }
}

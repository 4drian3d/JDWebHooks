package io.github._4drian3d.jdwebhooks.component;

import org.jetbrains.annotations.*;

/**
 * Base class for all components, containing the required type and optional id fields.
 */
public class Component {
    @NotNull
    private final ComponentType type;

    private final int id;

    Component(@NotNull final ComponentType type, final int id) {
        this.type = type;
        this.id = id;
    }

    @NotNull
    public ComponentType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public static SectionComponent.Builder section() {
        return new SectionComponent.Builder();
    }

    public static TextDisplayComponent.Builder textDisplay() {
        return new TextDisplayComponent.Builder();
    }

    public static ThumbnailComponent.Builder thumbnail(@NotNull final String media) {
        return new ThumbnailComponent.Builder(media);
    }

    public static MediaGalleryComponent.Builder mediaGallery() {
        return new MediaGalleryComponent.Builder();
    }

    abstract static class Builder<T extends Builder<T>> {
        protected int id;

        public Builder() {
            this.id = 0;
        }

        @SuppressWarnings("unchecked")
        public T setId(final int id) {
            this.id = id;
            return (T) this;
        }

        public abstract Component build();
    }
}
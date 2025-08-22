package io.github._4drian3d.jdwebhooks.component;

/**
 * The component types as described in the Discord API documentation.
 *
 * @see <a href="https://discord.com/developers/docs/components/reference#component-object-component-types">Component Types</a>
 */
public enum ComponentType {
    SECTION(9),
    TEXT_DISPLAY(10),
    THUMBNAIL(11),
    MEDIA_GALLERY(12),
    FILE(13),
    SEPARATOR(14),
    CONTAINER(17);

    private final int type;

    ComponentType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

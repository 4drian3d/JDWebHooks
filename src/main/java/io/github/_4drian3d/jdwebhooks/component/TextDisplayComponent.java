package io.github._4drian3d.jdwebhooks.component;

import org.jetbrains.annotations.*;

/**
 * A Text Display is a top-level content component that allows you to add text to your message formatted with markdown and mention users and roles.
 * This is similar to the content field of a message, but allows you to add multiple text components, controlling the layout of your message.
 *
 * @see <a href="https://discord.com/developers/docs/components/reference#text-display">Text Display Component</a>
 */
public final class TextDisplayComponent extends Component {
    @NotNull
    private final String content;

    TextDisplayComponent(@NotNull final String content, final int id) {
        super(ComponentType.TEXT_DISPLAY, id);
        this.content = content;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    public static final class Builder extends Component.Builder<Builder> {
        @NotNull
        private String content;

        Builder() {
            super();
            this.content = "";
        }

        /**
         * Sets the text content for this text display component.
         *
         * @param content the text content
         * @return this builder
         */
        public Builder setContent(@NotNull final String content) {
            this.content = content;
            return this;
        }

        @Override
        public TextDisplayComponent build() {
            return new TextDisplayComponent(content, id);
        }
    }
}

package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * A Text Display is a top-level content component that allows you to add text to your message formatted with markdown and mention users and roles.
 * This is similar to the content field of a message, but allows you to add multiple text components, controlling the layout of your message.
 *
 * @see <a href="https://discord.com/developers/docs/components/reference#text-display">Text Display Component</a>
 */
@NullMarked
record TextDisplayComponentImpl(
    @Nullable Integer id,
    ComponentType componentType,
    String content
) implements TextDisplayComponent {

  TextDisplayComponentImpl(final @Nullable Integer id, final String content) {
    this(id, ComponentType.TEXT_DISPLAY, content);
  }

  @NullUnmarked
  public static final class Builder extends AbstractComponentBuilder<TextDisplayComponent, TextDisplayComponent.Builder> implements TextDisplayComponent.Builder {
    private String content;

    /**
     * Sets the text content for this text display component.
     *
     * @param content the text content
     * @return this builder
     */
    @Override
    public Builder content(final @NonNull String content) {
      this.content = content;
      return this;
    }

    @Override
    public TextDisplayComponent build() {
      requireNonNull(content, "content");
      return new TextDisplayComponentImpl(id, content);
    }
  }
}

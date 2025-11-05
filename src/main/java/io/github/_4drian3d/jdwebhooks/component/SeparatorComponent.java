package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NullUnmarked;

/**
 * A Separator is a top-level layout component that adds vertical padding and visual division between other components.
 *
 * @see Component#separator()
 */
@NullUnmarked
public sealed interface SeparatorComponent extends Component, ContainerableComponent permits SeparatorComponentImpl {

  /**
   * Whether a visual divider should be displayed in the component. Defaults to true
   * @return Whether a visual divider should be displayed
   */
  Boolean divider();

  /**
   * Size of separator padding.
   * Defaults to {@link Spacing#SMALL}
   * @return the size of separator padding
   */
  Spacing spacing();

  enum Spacing {
    SMALL(1),
    LARGE(2);

    private final int value;

    Spacing(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  sealed interface Builder extends ComponentBuilder<SeparatorComponent, Builder> permits SeparatorComponentImpl.Builder {
    Builder divider(final Boolean divider);

    Builder spacing(final Spacing spacing);
  }
}

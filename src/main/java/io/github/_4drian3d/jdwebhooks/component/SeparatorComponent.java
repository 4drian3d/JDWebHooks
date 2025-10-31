package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NullUnmarked;

@NullUnmarked
public sealed interface SeparatorComponent extends Component, ContainerableComponent permits SeparatorComponentImpl {

  Boolean divider();

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

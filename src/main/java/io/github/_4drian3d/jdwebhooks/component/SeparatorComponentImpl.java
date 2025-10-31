package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

record SeparatorComponentImpl(
    Integer id,
    ComponentType componentType,
    Boolean divider,
    SeparatorComponent.@NonNull Spacing spacing
) implements SeparatorComponent {

  SeparatorComponentImpl(final @Nullable Integer id, final Boolean divider, final Spacing spacing) {
    this(id, ComponentType.SEPARATOR, divider, spacing);
  }

  static final class Builder extends AbstractComponentBuilder<SeparatorComponent, SeparatorComponent.Builder> implements SeparatorComponent.Builder {
    private Boolean divider;
    private Spacing spacing;

    @Override
    public Builder divider(final Boolean divider) {
      this.divider = divider;
      return this;
    }

    @Override
    public Builder spacing(final Spacing spacing) {
      this.spacing = spacing;
      return this;
    }

    @Override
    public SeparatorComponent build() {
      return new SeparatorComponentImpl(id, this.divider, spacing);
    }
  }
}

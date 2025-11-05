package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

record ContainerComponentImpl(
    @Nullable Integer id,
    @NonNull ComponentType componentType,
    @NonNull List<@NonNull ContainerableComponent> components,
    @Nullable Integer accentColor,
    @Nullable Boolean spoiler
) implements ContainerComponent {

  ContainerComponentImpl(Integer id, List<ContainerableComponent> components, Integer accentColor, Boolean spoiler) {
    this(id, ComponentType.CONTAINER, List.copyOf(components), accentColor, spoiler);
  }

  static final class BuilderImpl
      extends AbstractComponentBuilder<ContainerComponent, ContainerComponent.Builder>
      implements ContainerComponent.Builder
  {
    private final List<ContainerableComponent> components = new ArrayList<>();
    private Integer accentColor;
    private Boolean spoiler;

    @Override
    public Builder component(final @NonNull ContainerableComponent component) {
      this.components.add(component);
      return this;
    }

    @Override
    public BuilderImpl components(final @NonNull ContainerableComponent @NonNull... components) {
      this.components.clear();
      Collections.addAll(this.components, components);
      return this;
    }

    @Override
    public BuilderImpl components(final @NonNull List<@NonNull ContainerableComponent> components) {
      this.components.clear();
      this.components.addAll(components);
      return this;
    }

    @Override
    public BuilderImpl accentColor(final Integer accentColor) {
      this.accentColor = accentColor;
      return this;
    }

    @Override
    public BuilderImpl spoiler(final Boolean spoiler) {
      this.spoiler = spoiler;
      return this;
    }

    @Override
    public ContainerComponentImpl build() {
      return new ContainerComponentImpl(id, components, accentColor, spoiler);
    }
  }
}

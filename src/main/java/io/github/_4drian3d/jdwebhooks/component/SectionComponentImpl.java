package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;


@NullMarked
record SectionComponentImpl(
    @Nullable Integer id,
    ComponentType componentType,
    List<TextDisplayComponent> components,
    AccessoryComponent accessory
) implements SectionComponent {

  SectionComponentImpl(final @Nullable Integer id, final List<TextDisplayComponent> components, final AccessoryComponent accessory) {
    this(id, ComponentType.SECTION, List.copyOf(components), accessory);
  }

  @NullUnmarked
  public static final class Builder extends AbstractComponentBuilder<SectionComponent, SectionComponent.Builder> implements SectionComponent.Builder {
    @NonNull
    private final List<@NonNull TextDisplayComponent> components = new ArrayList<>();
    private AccessoryComponent accessory;

    @Override
    public Builder component(@NonNull final TextDisplayComponent component) {
      requireNonNull(component);
      this.components.add(component);
      return this;
    }

    @Override
    public Builder components(@NonNull final TextDisplayComponent @NonNull... components) {
      requireNonNull(components);
      this.components.clear();
      Collections.addAll(this.components, components);
      return this;
    }

    @Override
    public Builder components(@NonNull final List<@NonNull TextDisplayComponent> components) {
      requireNonNull(components);
      this.components.clear();
      this.components.addAll(components);
      return this;
    }

    @Override
    public Builder accessory(@NonNull final AccessoryComponent accessory) {
      this.accessory = accessory;
      return this;
    }

    @Override
    public SectionComponent build() {
      requireNonNull(accessory, "Accessory component must be provided.");
      requireNonNull(components);
      if (components.isEmpty() || components.size() > 3) {
        throw new IllegalArgumentException("Section component must have between 1 and 3 text display components.");
      }
      return new SectionComponentImpl(id, components, accessory);
    }
  }
}

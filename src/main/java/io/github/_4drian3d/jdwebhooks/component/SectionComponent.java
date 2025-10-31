package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public sealed interface SectionComponent extends Component, ContainerableComponent permits SectionComponentImpl {

  List<TextDisplayComponent> components();

  AccessoryComponent accessory();

  sealed interface Builder extends ComponentBuilder<SectionComponent, Builder> permits SectionComponentImpl.Builder {
    Builder component(final TextDisplayComponent component);

    Builder components(final TextDisplayComponent... components);

    Builder components(final List<TextDisplayComponent> components);

    Builder accessory(final AccessoryComponent accessory);
  }
}

package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * A Section is a top-level layout component that allows you to contextually associate content with an {@link AccessoryComponent}.
 * The typical use-case is to contextually associate text content with an accessory.
 *
 * @see Component#section()
 * @see TextDisplayComponent
 * @see AccessoryComponent
 * @see ThumbnailComponent
 */
@NullMarked
public sealed interface SectionComponent extends Component, ContainerableComponent permits SectionComponentImpl {

  /**
   * One to three child components representing the content of the section that is contextually associated to the accessor
   * @return content of the section
   */
  List<TextDisplayComponent> components();

  /**
   * A component that is contextually associated to the content of the section
   * @return the accessory component
   */
  AccessoryComponent accessory();

  sealed interface Builder extends ComponentBuilder<SectionComponent, Builder> permits SectionComponentImpl.Builder {
    Builder component(final TextDisplayComponent component);

    Builder components(final TextDisplayComponent... components);

    Builder components(final List<TextDisplayComponent> components);

    Builder accessory(final AccessoryComponent accessory);
  }
}

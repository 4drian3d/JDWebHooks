package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * A Container is a top-level layout component.
 * <br>
 * Containers offer the ability to visually encapsulate a collection of components and have an optional customizable accent color bar.
 */
public sealed interface ContainerComponent extends Component permits ContainerComponentImpl {
  @NonNull
  List<ContainerableComponent> components();

  @Nullable
  Integer accentColor();

  @Nullable
  Boolean spoiler();

  sealed interface Builder extends ComponentBuilder<ContainerComponent, Builder> permits ContainerComponentImpl.BuilderImpl {
    Builder component(@NonNull final ContainerableComponent component);

    Builder components(@NonNull final ContainerableComponent... components);

    Builder components(@NonNull final List<@NonNull ContainerableComponent> components);

    Builder accentColor(final Integer accentColor);

    Builder spoiler(final Boolean spoiler);
  }
}

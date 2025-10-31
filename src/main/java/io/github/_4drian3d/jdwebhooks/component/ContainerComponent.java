package io.github._4drian3d.jdwebhooks.component;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public sealed interface ContainerComponent extends Component permits ContainerComponentImpl {
  @NonNull
  List<ContainerableComponent> components();

  @Nullable
  Integer accentColor();

  @Nullable
  Boolean spoiler();

  sealed interface Builder extends ComponentBuilder<ContainerComponent, Builder> permits ContainerComponentImpl.BuilderImpl {
    Builder component(@NotNull final ContainerableComponent component);

    Builder components(@NotNull final ContainerableComponent... components);

    Builder components(@NotNull final List<@NotNull ContainerableComponent> components);

    Builder accentColor(final Integer accentColor);

    Builder spoiler(final Boolean spoiler);
  }
}

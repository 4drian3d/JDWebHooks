package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public sealed interface FileComponent extends Component, ContainerableComponent permits FileComponentImpl {
  @NonNull
  String file();

  @Nullable
  Boolean spoiler();

  sealed interface Builder extends ComponentBuilder<FileComponent, Builder> permits FileComponentImpl.Builder {
    Builder file(@NonNull final String file);

    Builder spoiler(final Boolean spoiler);
  }
}

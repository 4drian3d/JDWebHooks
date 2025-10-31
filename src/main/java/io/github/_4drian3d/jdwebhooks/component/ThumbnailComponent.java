package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public sealed interface ThumbnailComponent extends Component, AccessoryComponent permits ThumbnailComponentImpl {
  String media();

  @Nullable String description();

  @Nullable Boolean spoiler();

  sealed interface Builder extends ComponentBuilder<ThumbnailComponent, Builder> permits ThumbnailComponentImpl.Builder {
    Builder media(final String media);

    Builder description(final @Nullable String description);

    Builder spoiler(final @Nullable Boolean spoiler);
  }
}

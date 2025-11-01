package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
public sealed interface MediaGalleryComponent extends Component, ContainerableComponent permits MediaGalleryComponentImpl {
  List<Item> items();

  static Item.Builder itemBuilder() {
    return new MediaGalleryComponentImpl.Item.Builder();
  }

  sealed interface Item permits MediaGalleryComponentImpl.Item {
    String media();

    @Nullable
    String description();

    @Nullable
    Boolean spoiler();

    sealed interface Builder permits MediaGalleryComponentImpl.Item.Builder {
      Builder media(final String media);

      Builder description(final @Nullable String description);

      Builder spoiler(final @Nullable Boolean spoiler);

      Item build();
    }
  }

  sealed interface Builder extends ComponentBuilder<MediaGalleryComponent, Builder> permits MediaGalleryComponentImpl.Builder {
    Builder item(final Item item);

    Builder items(final Item... items);

    Builder items(final List<Item> items);
  }
}

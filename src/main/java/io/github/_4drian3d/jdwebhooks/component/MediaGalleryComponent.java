package io.github._4drian3d.jdwebhooks.component;

import io.github._4drian3d.jdwebhooks.media.FileAttachment;
import io.github._4drian3d.jdwebhooks.media.MediaReference;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * A Media Gallery is a top-level content component that allows you to display 1-10 media attachments
 * in an organized gallery format. Each item can have optional descriptions and can be marked as spoilers.
 *
 * @see Component#mediaGallery()
 * @see FileAttachment
 */
@NullMarked
public sealed interface MediaGalleryComponent extends Component, ContainerableComponent permits MediaGalleryComponentImpl {
  /**
   * 1 to 10 media gallery items
   * @return media gallery items
   */
  List<Item> items();

  static Item.Builder itemBuilder() {
    return new MediaGalleryComponentImpl.Item.Builder();
  }

  /**
   * MediaGallery Item
   * @see MediaGalleryComponent#itemBuilder()
   */
  sealed interface Item permits MediaGalleryComponentImpl.Item {
    /**
     * A url or attachment provided as an unfurled media item
     * @return an unfurled media item
     */
    MediaReference media();

    /**
     * Alt text for the media, max 1024 characters
     * @return Alt text for the media
     */
    @Nullable
    String description();

    /**
     * Whether the media should be a spoiler (or blurred out). Defaults to false
     * @return Whether the media should be a spoiler
     */
    @Nullable
    Boolean spoiler();

    sealed interface Builder permits MediaGalleryComponentImpl.Item.Builder {
      Builder media(final MediaReference mediaReference);

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

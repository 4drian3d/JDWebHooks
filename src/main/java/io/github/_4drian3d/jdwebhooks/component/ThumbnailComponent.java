package io.github._4drian3d.jdwebhooks.component;

import io.github._4drian3d.jdwebhooks.webhook.FileAttachment;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.net.URI;

/**
 * A Thumbnail is a content component that displays visual media in a small form-factor.
 * It is intended as an accessory for to other content, and is primarily usable with {@link SectionComponent}.
 * The {@link #media()} displayed is defined by the unfurled media item structure, which supports both uploaded media and externally hosted media.
 * <br>
 * Thumbnails are currently only available in messages as an accessory in a section.
 * <br>
 * Thumbnails currently only support images, including animated formats like GIF and WEBP.
 * Videos are not supported at this time.
 */
@NullMarked
public sealed interface ThumbnailComponent extends Component, AccessoryComponent permits ThumbnailComponentImpl {
  String media();

  @Nullable String description();

  @Nullable Boolean spoiler();

  sealed interface Builder extends ComponentBuilder<ThumbnailComponent, Builder> permits ThumbnailComponentImpl.Builder {
    Builder media(final URI mediaURI);

    Builder media(final FileAttachment fileAttachment);

    Builder description(final @Nullable String description);

    Builder spoiler(final @Nullable Boolean spoiler);
  }
}

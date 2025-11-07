package io.github._4drian3d.jdwebhooks.media;

import io.github._4drian3d.jdwebhooks.component.Component;
import io.github._4drian3d.jdwebhooks.webhook.WebHookExecution;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A reference to a file included in the webhook to be sent.
 *
 * @see io.github._4drian3d.jdwebhooks.component.FileComponent
 * @see MediaReference
 * @see WebHookExecution.Builder#components(Component...)
 * @see WebHookExecution.Builder#fileAttachments(FileAttachment...)
 * @see io.github._4drian3d.jdwebhooks.component.ThumbnailComponent.Builder#media(MediaReference)
 * @see io.github._4drian3d.jdwebhooks.component.MediaGalleryComponent.Item.Builder#media(MediaReference)
 */
@NullMarked
public sealed interface FileAttachment extends MediaReference permits FileAttachmentImpl {
  String PREFIX = "attachment://";

  Path file();

  String filename();

  @Nullable
  String description();

  @Override
  default String mediaReference() {
    return PREFIX + this.filename();
  }

  static FileAttachment.Builder builder() {
    return new FileAttachmentImpl.Builder();
  }

  static FileAttachment fromFile(final Path path) {
    requireNonNull(path);
    if (!Files.exists(path)) {
      throw new IllegalArgumentException("The file attachment must exist");
    }
    if (!Files.isRegularFile(path)) {
      throw new IllegalArgumentException("The file attachment must be a valid file");
    }
    return new FileAttachmentImpl(path, path.getFileName().toString(), null);
  }

  sealed interface Builder permits FileAttachmentImpl.Builder {
    Builder file(final Path file);

    Builder filename(final String filename);

    Builder description(final @Nullable String description);

    FileAttachment build();
  }
}

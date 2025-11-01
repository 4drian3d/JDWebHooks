package io.github._4drian3d.jdwebhooks.webhook;

import org.jspecify.annotations.NonNull;

import java.nio.file.Path;

public sealed interface FileAttachment permits FileAttachmentImpl {
  @NonNull
  Path file();

  @NonNull
  String filename();

  String description();

  static FileAttachment.Builder builder() {
    return new FileAttachmentImpl.Builder();
  }

  sealed interface Builder permits FileAttachmentImpl.Builder {
    Builder file(final Path file);

    Builder filename(String filename);

    Builder description(String description);

    FileAttachment build();
  }
}

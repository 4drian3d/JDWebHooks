package io.github._4drian3d.jdwebhooks.media;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

record FileAttachmentImpl(@NonNull Path file, @NonNull String filename, String description) implements FileAttachment {
  static final class Builder implements FileAttachment.Builder {
    private Path file;
    private String filename;
    private String description;

    @NonNull
    @Override
    public Builder file(final @NonNull Path file) {
      this.file = file;
      return this;
    }

    @NonNull
    @Override
    public Builder filename(@NonNull String filename) {
      this.filename = filename;
      return this;
    }

    @NonNull
    @Override
    public Builder description(final @Nullable String description) {
      this.description = description;
      return this;
    }

    @NonNull
    @Override
    public FileAttachmentImpl build() {
      requireNonNull(file);
      if (filename == null) {
        filename = file.getFileName().toString();
      }
      if (!Files.exists(file) || !Files.isRegularFile(file)) {
        throw new IllegalArgumentException("File must exist and be a valid file");
      }
      return new FileAttachmentImpl(file, filename, description);
    }
  }
}
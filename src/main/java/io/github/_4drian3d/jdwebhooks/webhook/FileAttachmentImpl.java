package io.github._4drian3d.jdwebhooks.webhook;

import org.jspecify.annotations.NonNull;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

record FileAttachmentImpl(@NonNull Path file, String filename, String description) implements FileAttachment {
  static final class Builder implements FileAttachment.Builder {
    private Path file;
    private String filename;
    private String description;

    public Builder file(final Path file) {
      this.file = file;
      return this;
    }

    public Builder filename(String filename) {
      this.filename = filename;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

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
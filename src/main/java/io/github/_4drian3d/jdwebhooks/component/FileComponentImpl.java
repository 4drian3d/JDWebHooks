package io.github._4drian3d.jdwebhooks.component;

import io.github._4drian3d.jdwebhooks.webhook.FileAttachment;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

@NullMarked
record FileComponentImpl(
    @Nullable Integer id,
    ComponentType componentType,
    String file,
    @Nullable Boolean spoiler
) implements FileComponent {

  FileComponentImpl(final @Nullable Integer id, final String file, final @Nullable Boolean spoiler) {
    this(id, ComponentType.FILE, file, spoiler);
  }

  @NullUnmarked
  static final class Builder extends AbstractComponentBuilder<FileComponent, FileComponent.Builder> implements FileComponent.Builder {
    private String file;
    private Boolean spoiler;

    @Override
    public Builder file(final @NonNull FileAttachment file) {
      this.file = file.filename();
      return this;
    }

    @Override
    public FileComponent.Builder file(final @NonNull Path file) {
      this.file = file.getFileName().toString();
      return this;
    }

    @Override
    public Builder spoiler(final Boolean spoiler) {
      this.spoiler = spoiler;
      return this;
    }

    @Override
    public FileComponentImpl build() {
      requireNonNull(file, "file");
      if (!file.startsWith(FileAttachment.PREFIX)) {
        file = FileAttachment.PREFIX + file;
      }
      return new FileComponentImpl(id, file, spoiler);
    }
  }
}

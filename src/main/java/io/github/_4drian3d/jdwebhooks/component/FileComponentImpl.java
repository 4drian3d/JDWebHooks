package io.github._4drian3d.jdwebhooks.component;

import io.github._4drian3d.jdwebhooks.media.FileAttachment;
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
    FileAttachment file,
    @Nullable Boolean spoiler
) implements FileComponent {

  FileComponentImpl(final @Nullable Integer id, final FileAttachment file, final @Nullable Boolean spoiler) {
    this(id, ComponentType.FILE, file, spoiler);
  }

  @NullUnmarked
  static final class Builder extends AbstractComponentBuilder<FileComponent, FileComponent.Builder> implements FileComponent.Builder {
    private FileAttachment file;
    private Boolean spoiler;

    @Override
    public Builder file(final @NonNull FileAttachment file) {
      this.file = file;
      return this;
    }

    @Override
    public FileComponent.Builder file(final @NonNull Path file) {
      this.file = FileAttachment.fromFile(file);
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
      return new FileComponentImpl(id, file, spoiler);
    }
  }
}

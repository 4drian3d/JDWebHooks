package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Base class for all components, containing the required type and optional id fields.
 */
@NullMarked
public sealed interface Component permits ContainerComponent, FileComponent, MediaGalleryComponent, SectionComponent, SeparatorComponent, TextDisplayComponent, ThumbnailComponent {
  ComponentType componentType();

  @Nullable
  Integer id();

  static SectionComponent.Builder section() {
    return new SectionComponentImpl.Builder();
  }

  static TextDisplayComponent.Builder textDisplay() {
    return new TextDisplayComponentImpl.Builder();
  }

  static ThumbnailComponent.Builder thumbnail() {
    return new ThumbnailComponentImpl.Builder();
  }

  static MediaGalleryComponent.Builder mediaGallery() {
    return new MediaGalleryComponentImpl.Builder();
  }

  static FileComponent.Builder file() {
    return new FileComponentImpl.Builder();
  }

  static SeparatorComponent.Builder separator() {
    return new SeparatorComponentImpl.Builder();
  }

  static ContainerComponent.Builder container() {
    return new ContainerComponentImpl.BuilderImpl();
  }
}
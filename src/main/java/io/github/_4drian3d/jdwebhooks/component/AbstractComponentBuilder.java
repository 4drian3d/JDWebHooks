package io.github._4drian3d.jdwebhooks.component;

public sealed abstract class AbstractComponentBuilder<R extends Component, T extends ComponentBuilder<R, T>> implements ComponentBuilder<R, T> permits ContainerComponentImpl.BuilderImpl, FileComponentImpl.Builder, MediaGalleryComponentImpl.Builder, SectionComponentImpl.Builder, SeparatorComponentImpl.Builder, TextDisplayComponentImpl.Builder, ThumbnailComponentImpl.Builder {
  protected Integer id;

  @Override
  @SuppressWarnings("unchecked")
  public T id(final int id) {
    this.id = id;
    return (T) this;
  }
}

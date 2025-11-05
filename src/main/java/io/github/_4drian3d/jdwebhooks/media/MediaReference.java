package io.github._4drian3d.jdwebhooks.media;

/**
 * This interface represents a reference to a file or image
 * that can be provided via an {@link URLMediaReference} or via {@link FileAttachment}.
 *
 * @see io.github._4drian3d.jdwebhooks.component.ThumbnailComponent.Builder#media(MediaReference)
 * @see io.github._4drian3d.jdwebhooks.component.MediaGalleryComponent.Item.Builder#media(MediaReference)
 */
public sealed interface MediaReference permits FileAttachment, URLMediaReference {
  String mediaReference();
}

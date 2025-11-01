package io.github._4drian3d.jdwebhooks.component;

import io.github._4drian3d.jdwebhooks.webhook.FileAttachment;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;

/**
 * A File is a top-level content component that allows you to display an uploaded file
 * as an attachment to the message and reference it in the component.
 * Each file component can only display 1 attached file,
 * but you can upload multiple files and add them to different file components within your payload.
 *
 * @see FileAttachment
 */
public sealed interface FileComponent extends Component, ContainerableComponent permits FileComponentImpl {
  @NonNull
  String file();

  @Nullable
  Boolean spoiler();

  sealed interface Builder extends ComponentBuilder<FileComponent, Builder> permits FileComponentImpl.Builder {
    Builder file(final @NonNull FileAttachment file);

    Builder file(final @NonNull Path file);

    Builder spoiler(final Boolean spoiler);
  }
}

package io.github._4drian3d.jdwebhooks.webhook;

import io.github._4drian3d.jdwebhooks.media.FileAttachment;
import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.component.Component;
import io.github._4drian3d.jdwebhooks.property.QueryParameters;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.util.List;

public sealed interface WebHookExecution permits WebHookExecutionImpl {

  @Nullable
  QueryParameters queryParameters();

  @Nullable
  String username();

  @Nullable
  URI avatarURL();

  @Nullable
  Boolean tts();

  @Nullable
  AllowedMentions allowedMentions();

  @Nullable
  String threadName();

  @NonNull
  List<Component> components();

  @Nullable
  List<FileAttachment> fileAttachments();

  @Nullable
  Boolean suppressNotifications();

  /**
   * Creates a new WebHookExecution Builder.
   *
   * @return a new WebHookExecution builder
   */
  static WebHookExecution.Builder builder() {
    return new WebHookExecutionImpl.Builder();
  }

  /**
   * WebHookExecutionImpl Builder
   */
  @NullMarked
  sealed interface Builder permits WebHookExecutionImpl.Builder {

    Builder queryParameters(QueryParameters queryParameters);
    /**
     * Sets the override username of this WebHookExecution.
     *
     * @param username the override username
     * @return this builder
     */
    Builder username(final @Nullable String username);

    /**
     * Sets the Avatar URL of this WebHookExecution
     *
     * @param avatarURL the Avatar URL
     * @return this builder
     */
    Builder avatarURL(final @Nullable String avatarURL);

    /**
     * Sets the Avatar URL of this WebHookExecution
     *
     * @param avatarURL the Avatar URL
     * @return this builder
     */
    Builder avatarURL(final @Nullable URI avatarURL);

    Builder tts(final @Nullable Boolean tts);

    Builder allowedMentions(final @Nullable AllowedMentions allowedMentions);

    Builder threadName(final @Nullable String threadName);

    Builder component(final Component component);

    Builder components(final List<Component> components);

    Builder components(Component... components);

    Builder fileAttachment(FileAttachment fileAttachment);

    Builder fileAttachments(List<FileAttachment> fileAttachments);

    Builder fileAttachments(FileAttachment... fileAttachments);

    Builder suppressNotifications(final @Nullable Boolean suppressNotifications);

    WebHookExecution build();
  }
}

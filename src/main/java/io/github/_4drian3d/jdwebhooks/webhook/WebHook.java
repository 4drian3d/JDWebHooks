package io.github._4drian3d.jdwebhooks.webhook;

import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.component.Component;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface WebHook {

  @Nullable
  QueryParameters queryParameters();

  @Nullable
  String username();

  @Nullable
  String avatarURL();

  @Nullable
  Boolean tts();

  @Nullable
  AllowedMentions allowedMentions();

  @Nullable
  String threadName();

  @NonNull
  List<Component> components();

  @Nullable
  Boolean suppressNotifications();

  /**
   * Creates a new WebHookImpl Builder.
   *
   * @return a new WebHookImpl builder
   */
  static WebHook.Builder builder() {
    return new WebHookImpl.Builder();
  }

  /**
   * WebHookImpl Builder
   */
  @NullMarked
  interface Builder {
    /**
     * Sets the override username of this WebHookImpl.
     *
     * @param username the override username
     * @return this builder
     */
    Builder username(final @Nullable String username);

    /**
     * Sets the Avatar URL of this WebHookImpl
     *
     * @param avatarURL the Avatar URL
     * @return this builder
     */
    Builder avatarURL(final @Nullable String avatarURL);

    Builder tts(final @Nullable Boolean tts);

    Builder allowedMentions(final @Nullable AllowedMentions allowedMentions);

    Builder threadName(final @Nullable String threadName);

    Builder component(final Component component);

    Builder components(final List<Component> components);

    Builder components(Component... components);

    Builder suppressNotifications(final @Nullable Boolean suppressNotifications);

    WebHook build();
  }


  /*
   * Sets the ID of an existing thread to send the message in.
   *
   * @param threadId The "thread_id" query parameter
   * @return this builder
   * @see <a href=https://discord.com/developers/docs/resources/webhook#execute-webhook-query-string-params>Execute Webhook Query String Params</a>
   */

  /*
   * Sets whether the response should wait for the message to be created.
   *
   * @param waitForMessage the "wait" query parameter
   * @return this builder
   * @see <a href=https://discord.com/developers/docs/resources/webhook#execute-webhook-query-string-params>Execute Webhook Query String Params</a>
   */
}

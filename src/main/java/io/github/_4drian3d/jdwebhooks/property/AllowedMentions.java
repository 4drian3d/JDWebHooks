package io.github._4drian3d.jdwebhooks.property;

import io.github._4drian3d.jdwebhooks.webhook.WebHookExecution;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Setting this field lets you determine whether users will receive notifications when you include mentions in the message content, or the content of components attached to that message.
 * This field is always validated against your permissions and the presence of said mentions in the message, to avoid "phantom" pings where users receive a notification without a visible mention in the message.
 * For example, if you want to ping everyone, including it in this field is not enough, the mention format ({@code @everyone}) must also be present in the content of the message or its components.
 * <br>
 * To mention roles and notify their members, the role's mentionable field must be set to true
 * Setting the {@link WebHookExecution.Builder#suppressNotifications(Boolean)} flag
 * when sending a message will disable push notifications and only cause a notification badge
 */
public sealed interface AllowedMentions permits AllowedMentionsImpl {
  /**
   * An array of allowed mention types to parse from the content
   * @return allowed mention types
   */
  @Nullable
  List<String> parse();

  /**
   * Array of role ids to mention, max 100
   * @return role ids to mention
   */
  @Nullable
  List<String> roles();

  /**
   * Array of user ids to mention, max 100
   * @return user ids to mention
   */
  @Nullable
  List<String> users();

  @Nullable
  Boolean repliedUser();

  /**
   * Convenient empty instance (no mentions, replied_user = false).
   */
  static AllowedMentions empty() {
    return new AllowedMentionsImpl(List.of(), List.of(), List.of(), Boolean.FALSE);
  }

  /**
   * Gets the default AllowedMentionsImpl instance, which parses users, roles and everyone.
   */
  static AllowedMentions getDefault() {
    return new AllowedMentionsImpl(
        List.of("users", "roles", "everyone"),
        List.of(),
        List.of(),
        Boolean.FALSE
    );
  }

  static AllowedMentions.Builder builder() {
    return new AllowedMentionsImpl.Builder();
  }

  /**
   * Builder for AllowedMentions
   */
  sealed interface Builder permits AllowedMentionsImpl.Builder {
    Builder parseUser(Boolean parseUser);

    Builder parseRoles(Boolean parseRoles);

    Builder parseEveryone(Boolean parseEveryone);

    Builder roles(Collection<String> ids);

    Builder addRole(String id);

    Builder users(Collection<String> ids);

    Builder addUser(String id);

    Builder repliedUser(Boolean replied);

    AllowedMentions build();
  }
}

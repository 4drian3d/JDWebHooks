package io.github._4drian3d.jdwebhooks.property;

import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public sealed interface AllowedMentions permits AllowedMentionsImpl {
  @Nullable
  List<String> parse();

  @Nullable
  List<String> roles();

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

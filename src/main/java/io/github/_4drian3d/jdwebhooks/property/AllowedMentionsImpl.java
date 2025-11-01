package io.github._4drian3d.jdwebhooks.property;

import org.jspecify.annotations.Nullable;

import java.util.*;

/**
 * Immutable wrapper for Discord's Allowed Mentions object.
 */
record AllowedMentionsImpl(
    @Nullable List<String> parse,
    @Nullable List<String> roles,
    @Nullable List<String> users,
    @Nullable Boolean repliedUser
) implements AllowedMentions {

  private static final Set<String> ALLOWED_PARSE = Set.of("users", "roles", "everyone");
  private static final int MAX_ID_ARRAY = 100;

  static final class Builder implements AllowedMentions.Builder {
    private final List<String> parse = new ArrayList<>();
    private final List<String> roles = new ArrayList<>();
    private final List<String> users = new ArrayList<>();
    private Boolean repliedUser = Boolean.FALSE;

    private void addParse(String token) {
      if (token == null) return;
      if (!ALLOWED_PARSE.contains(token)) {
        throw new IllegalArgumentException("Invalid parse token: " + token);
      }
      if (!parse.contains(token)) parse.add(token);
    }

    public Builder parseUser(Boolean parseUser) {
      if (parseUser == null || !parseUser) {
        parse.remove("users");
      } else {
        addParse("users");
      }
      return this;
    }

    public Builder parseRoles(Boolean parseRoles) {
      if (parseRoles == null || !parseRoles) {
        parse.remove("roles");
      } else {
        addParse("roles");
      }
      return this;
    }

    public Builder parseEveryone(Boolean parseEveryone) {
      if (parseEveryone == null || !parseEveryone) {
        parse.remove("everyone");
      } else {
        addParse("everyone");
      }
      return this;
    }

    public Builder roles(Collection<String> ids) {
      if (ids == null) return this;
      roles.clear();
      for (String id : ids) addRole(id);
      return this;
    }

    public Builder addRole(String id) {
      if (id == null) return this;
      if (roles.size() >= MAX_ID_ARRAY) {
        throw new IllegalStateException("Cannot add more than " + MAX_ID_ARRAY + " role ids");
      }
      roles.add(id);
      return this;
    }

    public Builder users(Collection<String> ids) {
      if (ids == null) return this;
      users.clear();
      for (String id : ids) addUser(id);
      return this;
    }

    public Builder addUser(String id) {
      if (id == null) return this;
      if (users.size() >= MAX_ID_ARRAY) {
        throw new IllegalStateException("Cannot add more than " + MAX_ID_ARRAY + " user ids");
      }
      users.add(id);
      return this;
    }

    public Builder repliedUser(Boolean replied) {
      this.repliedUser = (replied == null) ? Boolean.FALSE : replied;
      return this;
    }

    public AllowedMentionsImpl build() {
      if (roles.size() > MAX_ID_ARRAY) {
        throw new IllegalArgumentException("roles cannot contain more than " + MAX_ID_ARRAY + " entries");
      }
      if (users.size() > MAX_ID_ARRAY) {
        throw new IllegalArgumentException("users cannot contain more than " + MAX_ID_ARRAY + " entries");
      }

      // check for mutual exclusivity: parse is mutually exclusive with roles and users
      if (parse.contains("users") && !users.isEmpty()) {
        throw new IllegalArgumentException("Cannot specify 'users' in parse and also provide user ids");
      }
      if (parse.contains("roles") && !roles.isEmpty()) {
        throw new IllegalArgumentException("Cannot specify 'roles' in parse and also provide role ids");
      }
      return new AllowedMentionsImpl(
          parse.isEmpty() ? List.of() : validateAndNormalizeParse(parse),
          roles.isEmpty() ? List.of() : List.copyOf(roles),
          users.isEmpty() ? List.of() : List.copyOf(users),
          repliedUser == null ? Boolean.FALSE : repliedUser
      );
    }

    private static List<String> validateAndNormalizeParse(Collection<String> input) {
      List<String> normalized = new ArrayList<>(input.size());
      for (String raw : input) {
        if (raw == null) continue;
        String token = raw.trim().toLowerCase(Locale.ROOT);
        if (!ALLOWED_PARSE.contains(token)) {
          throw new IllegalArgumentException("Invalid parse token: " + raw + ". Allowed: " + ALLOWED_PARSE);
        }
        if (!normalized.contains(token)) { // avoid duplicates
          normalized.add(token);
        }
      }
      return normalized;
    }
  }
}

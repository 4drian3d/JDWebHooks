package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.property.AllowedMentions;

import java.lang.reflect.*;

public final class AllowedMentionsSerializer implements JsonSerializer<AllowedMentions>, CommonSerializer {
  @Override
  public JsonElement serialize(AllowedMentions allowedMentions, Type type, JsonSerializationContext context) {
    final JsonObject object = new JsonObject();

    final var parse = allowedMentions.parse();
    // we don't check for empty, because an empty parse list has a different meaning than no parse at all
    if (parse != null) {
      object.add("parse", context.serialize(parse));
    }

    final var roles = allowedMentions.roles();
    if (roles != null && !roles.isEmpty()) {
      object.add("roles", context.serialize(roles));
    }

    final var users = allowedMentions.users();
    if (users != null && !users.isEmpty()) {
      object.add("users", context.serialize(users));
    }

    final var repliedUser = allowedMentions.repliedUser();
    if (Boolean.TRUE.equals(repliedUser)) {
      object.addProperty("replied_user", true);
    }

    return object;
  }
}

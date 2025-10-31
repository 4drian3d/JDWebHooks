package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.github._4drian3d.jdwebhooks.webhook.WebHook;

import java.lang.reflect.Type;

public final class WebHookSerializer implements JsonSerializer<WebHook>, CommonSerializer {
  @Override
  public JsonElement serialize(
      final WebHook src,
      final Type typeOfSrc,
      final JsonSerializationContext context
  ) {
    final JsonObject object = new JsonObject();
    this.addNonNull(object, "username", src.username());
    this.addNonNull(object, "avatar_url", src.avatarURL());
    this.addNonNull(object, "tts", src.tts());
    this.addNonNull(object, "thread_name", src.threadName());

    final var allowedMentions = src.allowedMentions();
    if (allowedMentions != null) {
      object.add("allowed_mentions", context.serialize(allowedMentions));
    }

    final var components = src.components();
    object.add("components", context.serialize(components));

    // also apply a 1 << 15 bitfield to "flags" to indicate the message contains components
    int flags = object.get("flags") != null ? object.get("flags").getAsInt() : 0;
    flags |= 1 << 15;

    final var suppressNotifications = src.suppressNotifications();
    if (suppressNotifications == Boolean.TRUE) {
      // apply a 1 << 12 bitfield
      flags |= 1 << 12;
    }
    object.addProperty("flags", flags);

    return object;
  }
}

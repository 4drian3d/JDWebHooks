package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.webhook.FileAttachment;
import io.github._4drian3d.jdwebhooks.webhook.WebHook;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.Type;
import java.util.List;

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

    final var attachments = src.fileAttachments();
    if (attachments != null && !attachments.isEmpty()) {
      var attachmentArray = getAttachments(attachments);
      object.add("attachments", attachmentArray);
    }

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

  private @NonNull JsonArray getAttachments(List<FileAttachment> attachments) {
    var attachmentArray = new JsonArray();
    for (var i = 0; i < attachments.size(); i++) {
      var attachment = attachments.get(i);

      var attachmentObject = new JsonObject();
      attachmentObject.addProperty("id", String.valueOf(i));
      attachmentObject.addProperty("filename", attachment.filename());
      this.addNonNull(attachmentObject, "description", attachment.description());

      attachmentArray.add(attachmentObject);
    }
    return attachmentArray;
  }
}

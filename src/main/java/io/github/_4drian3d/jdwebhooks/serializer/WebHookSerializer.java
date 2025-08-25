package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.*;
import org.jetbrains.annotations.*;

import java.lang.reflect.*;
import java.util.*;

public final class WebHookSerializer implements JsonSerializer<WebHook>, CommonSerializer {
    @Override
    public JsonElement serialize(
            final WebHook src,
            final Type typeOfSrc,
            final JsonSerializationContext context
    ) {
        final JsonObject object = new JsonObject();
        object.addProperty("content", src.content());
        this.addNonNull(object, "username", src.username());
        this.addNonNull(object, "avatar_url", src.avatarURL());
        this.addNonNull(object, "tts", src.tts());
        this.addNonNull(object, "thread_name", src.threadName());

        final var allowedMentions = src.allowedMentions();
        if (allowedMentions != null) {
            object.add("allowed_mentions", context.serialize(allowedMentions));
        }

        final var embeds = src.embeds();
        if (embeds != null && !embeds.isEmpty()) {
            object.add("embeds", context.serialize(embeds));
        }

        final var components = src.components();
        if (components != null && !components.isEmpty()) {
            object.add("components", context.serialize(components));

            // also apply a 1 << 15 bitfield to "flags" to indicate the message contains components
            final int existingFlags = object.get("flags") != null ? object.get("flags").getAsInt() : 0;
            object.addProperty("flags", existingFlags | (1 << 15));
        }

        final var attachments = src.attachments();
        if (attachments != null && !attachments.isEmpty()) {
            var attachmentArray = getAttachments(attachments);
            object.add("attachments", attachmentArray);
        }

        final var suppressEmbeds = src.suppressEmbeds();
        if(suppressEmbeds == Boolean.TRUE) {
            // apply a 1 << 2 bitfield
            final int existingFlags = object.get("flags") != null ? object.get("flags").getAsInt() : 0;
            object.addProperty("flags", existingFlags | (1 << 2));
        }

        final var suppressNotifications = src.suppressNotifications();
        if(suppressNotifications == Boolean.TRUE) {
            // apply a 1 << 12 bitfield
            final int existingFlags = object.get("flags") != null ? object.get("flags").getAsInt() : 0;
            object.addProperty("flags", existingFlags | (1 << 12));
        }

        return object;
    }

    private @NotNull JsonArray getAttachments(List<FileAttachment> attachments) {
        var attachmentArray = new JsonArray();
        for (var i = 0; i < attachments.size(); i++) {
            var attachment = attachments.get(i);

            var attachmentObject = new JsonObject();
            attachmentObject.addProperty("id", String.valueOf(i));
            attachmentObject.addProperty("filename", attachment.getFilename());
            this.addNonNull(attachmentObject, "description", attachment.description());

            attachmentArray.add(attachmentObject);
        }
        return attachmentArray;
    }
}

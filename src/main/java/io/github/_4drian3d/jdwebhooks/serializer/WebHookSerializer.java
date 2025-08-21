package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.github._4drian3d.jdwebhooks.WebHook;

import java.lang.reflect.Type;

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
        if (embeds != null) {
            object.add("embeds", context.serialize(embeds));
        }

        return object;
    }
}

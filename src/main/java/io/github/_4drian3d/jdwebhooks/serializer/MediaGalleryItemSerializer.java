package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.component.*;

import java.lang.reflect.*;

public class MediaGalleryItemSerializer implements JsonSerializer<MediaGalleryComponent.Item> {
    @Override
    public JsonElement serialize(MediaGalleryComponent.Item src, Type typeOfSrc, JsonSerializationContext context) {
        final var object = new JsonObject();

        final var mediaObject = new JsonObject();
        mediaObject.addProperty("url", src.media());
        object.add("media", mediaObject);

        final var description = src.description();
        if (description != null) {
            object.addProperty("description", description);
        }

        final var spoiler = src.spoiler();
        if (spoiler == Boolean.TRUE) {
            object.addProperty("spoiler", true);
        }

        return object;
    }
}

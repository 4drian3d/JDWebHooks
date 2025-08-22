package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.component.*;

import java.lang.reflect.*;

public class ComponentSerializer implements JsonSerializer<Component> {
    @Override
    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject object = new JsonObject();

        object.addProperty("type", src.getType().getType());

        final var id = src.getId();
        if (id != 0) {
            // id 0 gets ignored by the API
            object.addProperty("id", src.getId());
        }

        if (src instanceof TextDisplayComponent textDisplay) {
            object.addProperty("content", textDisplay.getContent());
        }

        if (src instanceof SectionComponent section) {
            object.add("components", context.serialize(section.getComponents()));
            object.add("accessory", context.serialize(section.getAccessory()));
        }

        if (src instanceof ThumbnailComponent thumbnail) {
            final var mediaObject = new JsonObject();
            mediaObject.addProperty("url", thumbnail.getMedia());
            object.add("media", mediaObject);

            final var description = thumbnail.getDescription();
            if (description != null) {
                object.addProperty("description", description);
            }

            final var spoiler = thumbnail.getSpoiler();
            if (spoiler == Boolean.TRUE) {
                object.addProperty("spoiler", true);
            }
        }

        if (src instanceof MediaGalleryComponent mediaGallery) {
            object.add("items", context.serialize(mediaGallery.getItems()));
        }

        if (src instanceof FileComponent file) {
            final var fileObject = new JsonObject();
            fileObject.addProperty("url", file.getFile());
            object.add("file", fileObject);

            final var spoiler = file.getSpoiler();
            if (spoiler == Boolean.TRUE) {
                object.addProperty("spoiler", true);
            }
        }

        return object;
    }
}

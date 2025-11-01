package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.component.*;

import java.lang.reflect.*;

public class ComponentSerializer implements JsonSerializer<Component> {
    @Override
    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject object = new JsonObject();
        object.addProperty("type", src.componentType().getType());

        final var id = src.id();
        if (id != null && id != 0) {
            // id 0 gets ignored by the API
            object.addProperty("id", src.id());
        }

        switch (src) {
          case TextDisplayComponent textDisplay ->
            object.addProperty("content", textDisplay.content());
          case SectionComponent section -> {
            object.add("components", context.serialize(section.components()));
            object.add("accessory", context.serialize(section.accessory()));
          }
          case ThumbnailComponent thumbnail -> {
            final var mediaObject = new JsonObject();
            mediaObject.addProperty("url", thumbnail.media());
            object.add("media", mediaObject);

            final var description = thumbnail.description();
            if (description != null) {
              object.addProperty("description", description);
            }

            final var spoiler = thumbnail.spoiler();
            if (spoiler == Boolean.TRUE) {
              object.addProperty("spoiler", true);
            }
          }
          case MediaGalleryComponent mediaGallery ->
            object.add("items", context.serialize(mediaGallery.items()));
          case FileComponent file -> {
            final var fileObject = new JsonObject();
            fileObject.addProperty("url", file.file());
            object.add("file", fileObject);

            final var spoiler = file.spoiler();
            if (spoiler == Boolean.TRUE) {
              object.addProperty("spoiler", true);
            }
          }
          case SeparatorComponent separator -> {
            final var divider = separator.divider();
            // true is the default value so we can ignore that
            if (divider == Boolean.FALSE) {
              object.addProperty("divider", false);
            }

            final var spacing = separator.spacing();
            if (spacing != null) {
              object.addProperty("spacing", spacing.getValue());
            }
          }
          case ContainerComponent container -> {
            object.add("components", context.serialize(container.components()));

            final var accentColor = container.accentColor();
            if (accentColor != null) {
              object.addProperty("accent_color", accentColor);
            }

            final var spoiler = container.spoiler();
            if (spoiler == Boolean.TRUE) {
              object.addProperty("spoiler", true);
            }
          }
        }

        return object;
    }
}

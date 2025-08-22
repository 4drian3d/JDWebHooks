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

        return object;
    }
}

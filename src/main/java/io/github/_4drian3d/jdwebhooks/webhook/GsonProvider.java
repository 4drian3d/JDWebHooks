package io.github._4drian3d.jdwebhooks.webhook;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.component.*;
import io.github._4drian3d.jdwebhooks.serializer.*;

import java.time.*;

public final class GsonProvider {
    public static Gson provide() {
        final var componentSerializer = new ComponentSerializer();

        return new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new DateSerializer())
                .registerTypeAdapter(WebHookImpl.class, new WebHookSerializer())
                .registerTypeAdapter(AllowedMentions.class, new AllowedMentionsSerializer())
                .registerTypeHierarchyAdapter(Component.class, componentSerializer)
                .registerTypeHierarchyAdapter(MediaGalleryComponent.Item.class, new MediaGalleryItemSerializer())
                .create();
    }
}

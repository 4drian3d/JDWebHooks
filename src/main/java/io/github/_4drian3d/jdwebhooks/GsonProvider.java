package io.github._4drian3d.jdwebhooks;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.component.*;
import io.github._4drian3d.jdwebhooks.serializer.*;

import java.time.*;

class GsonProvider {
    static Gson getGson() {
        final var componentSerializer = new ComponentSerializer();

        return new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new DateSerializer())
                .registerTypeAdapter(Embed.class, new EmbedSerializer())
                .registerTypeAdapter(WebHook.class, new WebHookSerializer())
                .registerTypeAdapter(AllowedMentions.class, new AllowedMentionsSerializer())
                .registerTypeAdapter(SectionComponent.class, componentSerializer)
                .registerTypeAdapter(TextDisplayComponent.class, componentSerializer)
                .registerTypeAdapter(ThumbnailComponent.class, componentSerializer)
                .registerTypeAdapter(MediaGalleryComponent.class, componentSerializer)
                .registerTypeAdapter(MediaGalleryComponent.Item.class, new MediaGalleryItemSerializer())
                .create();
    }
}

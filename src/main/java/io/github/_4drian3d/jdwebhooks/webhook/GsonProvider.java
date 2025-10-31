package io.github._4drian3d.jdwebhooks.webhook;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.component.*;
import io.github._4drian3d.jdwebhooks.serializer.*;

import java.time.*;

class GsonProvider {
    static Gson getGson() {
        final var componentSerializer = new ComponentSerializer();

        return new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new DateSerializer())
                .registerTypeAdapter(WebHookImpl.class, new WebHookSerializer())
                .registerTypeAdapter(AllowedMentions.class, new AllowedMentionsSerializer())
                .registerTypeAdapter(SectionComponent.class, componentSerializer)
                .registerTypeAdapter(TextDisplayComponent.class, componentSerializer)
                .registerTypeAdapter(ThumbnailComponent.class, componentSerializer)
                .registerTypeAdapter(MediaGalleryComponent.class, componentSerializer)
                .registerTypeAdapter(FileComponent.class, componentSerializer)
                .registerTypeAdapter(SeparatorComponent.class, componentSerializer)
                .registerTypeAdapter(ContainerComponent.class, componentSerializer)
                .registerTypeAdapter(MediaGalleryComponent.Item.class, new MediaGalleryItemSerializer())
                .create();
    }
}

package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github._4drian3d.jdwebhooks.component.Component;
import io.github._4drian3d.jdwebhooks.component.MediaGalleryComponent;
import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.webhook.WebHookExecution;

import java.time.OffsetDateTime;

public final class GsonProvider {
  public static Gson provide() {
    return new GsonBuilder()
        .registerTypeAdapter(OffsetDateTime.class, new DateSerializer())
        .registerTypeHierarchyAdapter(WebHookExecution.class, new WebHookSerializer())
        .registerTypeHierarchyAdapter(AllowedMentions.class, new AllowedMentionsSerializer())
        .registerTypeHierarchyAdapter(Component.class, new ComponentSerializer())
        .registerTypeHierarchyAdapter(MediaGalleryComponent.Item.class, new MediaGalleryItemSerializer())
        .create();
  }
}

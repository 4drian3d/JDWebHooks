package io.github._4drian3d.jdwebhooks.webhook;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.component.*;
import io.github._4drian3d.jdwebhooks.serializer.*;

import java.nio.file.Path;
import java.time.*;
import java.util.List;

public final class GsonProvider {
  public static Gson provide() {
    final var componentSerializer = new ComponentSerializer();

    return new GsonBuilder()
        .registerTypeAdapter(OffsetDateTime.class, new DateSerializer())
        .registerTypeAdapter(WebHookImpl.class, new WebHookSerializer())
        .registerTypeHierarchyAdapter(AllowedMentions.class, new AllowedMentionsSerializer())
        .registerTypeHierarchyAdapter(Component.class, componentSerializer)
        .registerTypeHierarchyAdapter(MediaGalleryComponent.Item.class, new MediaGalleryItemSerializer())
        .create();
  }

  public static void main(String[] args) {
    // Yeah, I known that this is "secret", but I'll delete later
    final WebHookClient client = WebHookClient.builder()
        .credentials("1195923086558646342", "Ss_HWj6j3UHfpn5TlASLlnS7ZvclwQipq0JNv92JACvPozCoMwELXN8jh1qw9UnNJUXL")
        .agent("JDWebHooks Testing xd")
        .build();

    final WebHook webHook = WebHook.builder()
        .username("Ola")
        .components(
            Component.textDisplay()
                .content("Contenido xd")
                .build(),
            Component.separator()
                .spacing(SeparatorComponent.Spacing.LARGE)
                .divider(true)
                .build(),
            Component.file().file("build.gradle.kts").build(),
            Component.container()
                .accentColor(0xFF0000)
                .components(
                    Component.textDisplay().content("Contenido en container").build(),
                    Component.mediaGallery().items(MediaGalleryComponent.itemBuilder()
                        .media("https://avatars.githubusercontent.com/u/68704415?v=4").build()).build()

                ).build()
        )
        .fileAttachments(List.of(
            FileAttachment.builder()
                .filename("build.gradle.kts")
                .file(Path.of("build.gradle.kts"))
                .build()
        ))
        .build();

    client.sendWebHook(webHook)
        .handle((stringHttpResponse, throwable) -> {
          System.out.println("Throwable: " + throwable);
          System.out.println("Response Code: " + stringHttpResponse.statusCode());
          System.out.println("Response Body: " + stringHttpResponse.body());
          System.out.println("Response Headers: " + stringHttpResponse.headers().toString());
          System.out.println("Response Request: " + stringHttpResponse.request().toString());
          System.out.println("Response Body publisher: " + stringHttpResponse.request().bodyPublisher().orElseThrow().toString());
          return null;
        }).join();
  }
}

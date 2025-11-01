package io.github._4drian3d.jdwebhooks.webhook;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.component.*;
import io.github._4drian3d.jdwebhooks.property.QueryParameters;
import io.github._4drian3d.jdwebhooks.serializer.*;

import java.net.URI;
import java.nio.file.Path;
import java.time.*;

public final class GsonProvider {
  public static Gson provide() {
    return new GsonBuilder()
        .registerTypeAdapter(OffsetDateTime.class, new DateSerializer())
        .registerTypeAdapter(WebHookImpl.class, new WebHookSerializer())
        .registerTypeHierarchyAdapter(AllowedMentions.class, new AllowedMentionsSerializer())
        .registerTypeHierarchyAdapter(Component.class, new ComponentSerializer())
        .registerTypeHierarchyAdapter(MediaGalleryComponent.Item.class, new MediaGalleryItemSerializer())
        .create();
  }

  public static void main(String[] args) {
    // Yeah, I known that this is "secret", but I'll delete later
    final WebHookClient client = WebHookClient.builder()
        .credentials("1195923086558646342", "Ss_HWj6j3UHfpn5TlASLlnS7ZvclwQipq0JNv92JACvPozCoMwELXN8jh1qw9UnNJUXL")
        .agent("JDWebHooks Testing xd")
        .build();


    final FileAttachment fileAttachment = FileAttachment.builder()
        .file(Path.of("build.gradle.kts")).build();
    final WebHook webHook = WebHook.builder()
        .username("4drian3d Super Hiper WebHook")
        .avatarURL("https://assets.papermc.io/brand/papermc_logo.256.png")
        .queryParameters(QueryParameters.builder().waitForMessage(true).build())
        .components(
            Component.container()
                .accentColor(0xFF0000)
                .components(
                    Component.section()
                        .accessory(Component.thumbnail().media(URI.create("https://avatars.githubusercontent.com/u/68704415?v=4")).build())
                        .components(Component.textDisplay("# Titulo Gigante"))
                        .build(),
                    Component.textDisplay("# Titulo"),
                    Component.separator()
                        .spacing(SeparatorComponent.Spacing.LARGE)
                        .divider(true)
                        .build(),
                    Component.textDisplay("""
                        ## Archivo
                        Esta wea tambien acepta multiline
                        Yeah"""),
                    Component.file().file(fileAttachment).build(),
                    Component.textDisplay().content("* Imagen: ").build(),
                    Component.mediaGallery()
                        .items(
                            MediaGalleryComponent.itemBuilder()
                                .media(URI.create("https://cdn.modrinth.com/data/sG6SrXta/d887dd162a7b3d9edc85e8b506da96b29f996000_96.webp"))
                                .build(),
                            MediaGalleryComponent.itemBuilder()
                                .media(URI.create("https://wsrv.nl/?url=https%3A%2F%2Fwww.bisecthosting.com%2Fpartners%2Fcustom-banners%2F6fa909d5-ad2b-42c2-a7ec-1c51f8b6384f.webp&n=-1"))
                                .spoiler(true).build()
                        )
                        .build()
                ).build()
        )
        .fileAttachment(fileAttachment)
        .build();

    client.sendWebHook(webHook)
        .handle((stringHttpResponse, throwable) -> {
          System.out.println("Throwable: " + throwable);
          System.out.println("Response Code: " + stringHttpResponse.statusCode());
          System.out.println("Response Body: " + stringHttpResponse.body());
          return null;
        }).join();
  }
}

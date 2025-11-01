package io.github._4drian3d.jdwebhooks;

import com.google.gson.JsonParser;
import io.github._4drian3d.jdwebhooks.component.*;
import io.github._4drian3d.jdwebhooks.property.QueryParameters;
import io.github._4drian3d.jdwebhooks.webhook.*;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class WebHookClientTest {
  static WebHookClient client;

  @BeforeAll
  static void beforeAll() {
    client = WebHookClient.fromURL(System.getenv("DISCORD_WEBHOOK_URL"));
  }

  @Test
  void testClientCreation() {
    assertNotNull(client);
  }

  @Test
  void testSuccess() {
    final WebHook webHook = WebHook.builder()
        .component(Component.textDisplay().content("testSuccess").build())
        .build();

    final var response = client.sendWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testBasicMessage() {
    final String username = "testing123";
    final String content = "testBasicMessage";

    final WebHook webHook = WebHook.builder()
        .username(username)
        .component(Component.textDisplay().content(content).build())
        .queryParameters(QueryParameters.builder().waitForMessage(true).build())
        .build();

    final var response = client.sendWebHook(webHook).join();
    assertEquals(200, response.statusCode());

    final var jsonBody = JsonParser.parseString(response.body());
    assertTrue(jsonBody.isJsonObject());
    JsonAssertions.assertThatJson(jsonBody).inPath("$.content").isEqualTo(content);
    JsonAssertions.assertThatJson(jsonBody).inPath("$.author.username").isEqualTo(username);
  }

  @Test
  void testTextDisplayComponent() throws ExecutionException, InterruptedException {
    final var component = Component.textDisplay().content("Text Display Component").build();

    final WebHook webHook = WebHook.builder()
        .component(component)
        .build();

    final var response = client.sendWebHook(webHook).get();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testSectionComponent() {
    final var textComponents = new ArrayList<TextDisplayComponent>();
    for (int i = 1; i <= 3; i++) {
      textComponents.add(Component.textDisplay().content("Text Component " + i).build());
    }

    final var avatarUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
    final var accessory = Component.thumbnail().media(avatarUrl).spoiler(true).description("Hi :)").build();

    final var component = Component.section().components(textComponents).accessory(accessory).build();
    final WebHook webHook = WebHook.builder()
        .component(component)
        .build();

    final var response = client.sendWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testMediaGalleryComponent() {
    // generate 10 random image urls
    final var mediaItems = new ArrayList<MediaGalleryComponent.Item>();
    for (int i = 1; i <= 9; i++) {
      final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
      final var mediaItem = MediaGalleryComponent.itemBuilder().media(imageUrl).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
      mediaItems.add(mediaItem);
    }

    final var component = Component.mediaGallery().items(mediaItems).build();
    final WebHook webHook = WebHook.builder()
        .component(component)
        .build();

    final var response = client.sendWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testSeparatorComponent() {
    final var text1 = Component.textDisplay().content("Above the separator").build();
    final var text2 = Component.textDisplay().content("Below the separator").build();
    final var separator = Component.separator().spacing(SeparatorComponent.Spacing.LARGE).build();

    final WebHook webHook = WebHook.builder()
        .components(text1, separator, text2)
        .build();

    final var response = client.sendWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testContainerComponent() {
    final var textComponent = Component.textDisplay().content("Inside Container").build();

    final var mediaItems = new ArrayList<MediaGalleryComponent.Item>();
    for (int i = 1; i <= 9; i++) {
      final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
      final var mediaItem = MediaGalleryComponent.itemBuilder().media(imageUrl).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
      mediaItems.add(mediaItem);
    }
    final var mediaComponent = Component.mediaGallery().items(mediaItems).build();

    final var container = Component.container().components(textComponent, mediaComponent).accentColor(0x123456).spoiler(true).build();

    final WebHook webHook = WebHook.builder()
        .component(container)
        .build();

    final var response = client.sendWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }
}
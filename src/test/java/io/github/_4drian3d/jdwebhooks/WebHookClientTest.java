package io.github._4drian3d.jdwebhooks;

import com.google.gson.JsonParser;
import io.github._4drian3d.jdwebhooks.component.Component;
import io.github._4drian3d.jdwebhooks.component.MediaGalleryComponent;
import io.github._4drian3d.jdwebhooks.component.SeparatorComponent;
import io.github._4drian3d.jdwebhooks.component.TextDisplayComponent;
import io.github._4drian3d.jdwebhooks.media.URLMediaReference;
import io.github._4drian3d.jdwebhooks.property.QueryParameters;
import io.github._4drian3d.jdwebhooks.webhook.WebHookExecution;
import io.github._4drian3d.jdwebhooks.webhook.WebHookClient;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.ArrayList;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;
import static org.junit.jupiter.api.Assertions.*;

@EnabledIfEnvironmentVariable(
    named = "DISCORD_WEBHOOK_URL",
    matches = "^https:\\/\\/discord\\.com\\/api\\/webhooks\\/\\d{17,20}\\/[a-zA-Z0-9_-]+$"
)
class WebHookClientTest {
  static WebHookClient client;

  @BeforeAll
  static void beforeAll() {
    client = WebHookClient.fromURL(
        System.getenv("DISCORD_WEBHOOK_URL"),
        "JDWebHooks by 4drian3d | JUnit Testing"
    );
  }

  @Test
  void testClientCreation() {
    assertNotNull(client);
  }

  @Test
  void testSuccess() {
    final WebHookExecution webHook = WebHookExecution.builder()
        .component(Component.textDisplay().content("testSuccess").build())
        .build();

    final var response = client.executeWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testBasicMessage() {
    final String username = "testing123";
    final String content = "testBasicMessage";

    final var response = client.executeWebHook(builder -> builder.username(username)
        .component(Component.textDisplay().content(content).build())
        .queryParameters(QueryParameters.builder().waitForMessage(true).build())
        .build()).join();
    assertEquals(200, response.statusCode());

    final var jsonBody = JsonParser.parseString(response.body());
    assertTrue(jsonBody.isJsonObject());
    JsonAssertions.assertThatJson(jsonBody)
        .when(Option.IGNORING_ARRAY_ORDER)
        .inPath("$.components")
        .isArray()
        .contains(json("""
            {"type":10,"id":1,"content":%s}""".formatted(content)
        ));
    JsonAssertions.assertThatJson(jsonBody).inPath("$.author.username").isEqualTo(username);
  }

  @Test
  void testTextDisplayComponent() {
    final var component = Component.textDisplay().content("Text Display Component").build();

    final var response = client.executeWebHook(builder -> builder.component(component).build()).join();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testSectionComponent() {
    final var textComponents = new ArrayList<TextDisplayComponent>();
    for (int i = 1; i <= 3; i++) {
      textComponents.add(Component.textDisplay().content("Text Component " + i).build());
    }

    final var avatarUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
    final var accessory = Component.thumbnail().media(URLMediaReference.from(avatarUrl)).spoiler(true).description("Hi :)").build();

    final var component = Component.section().components(textComponents).accessory(accessory).build();
    final WebHookExecution webHook = WebHookExecution.builder()
        .component(component)
        .build();

    final var response = client.executeWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testMediaGalleryComponent() {
    // generate 10 random image urls
    final var mediaItems = new ArrayList<MediaGalleryComponent.Item>();
    for (int i = 1; i <= 9; i++) {
      final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
      final var mediaItem = MediaGalleryComponent.itemBuilder().media(URLMediaReference.from(imageUrl)).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
      mediaItems.add(mediaItem);
    }

    final var component = Component.mediaGallery().items(mediaItems).build();
    final WebHookExecution webHook = WebHookExecution.builder()
        .component(component)
        .build();

    final var response = client.executeWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testSeparatorComponent() {
    final var text1 = Component.textDisplay().content("Above the separator").build();
    final var text2 = Component.textDisplay().content("Below the separator").build();
    final var separator = Component.separator().spacing(SeparatorComponent.Spacing.LARGE).build();

    final WebHookExecution webHook = WebHookExecution.builder()
        .components(text1, separator, text2)
        .build();

    final var response = client.executeWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }

  @Test
  void testContainerComponent() {
    final var textComponent = Component.textDisplay().content("Inside Container").build();

    final var mediaItems = new ArrayList<MediaGalleryComponent.Item>();
    for (int i = 1; i <= 9; i++) {
      final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
      final var mediaItem = MediaGalleryComponent.itemBuilder().media(URLMediaReference.from(imageUrl)).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
      mediaItems.add(mediaItem);
    }
    final var mediaComponent = Component.mediaGallery().items(mediaItems).build();

    final var container = Component.container().components(textComponent, mediaComponent).accentColor(0x123456).spoiler(true).build();

    final WebHookExecution webHook = WebHookExecution.builder()
        .component(container)
        .build();

    final var response = client.executeWebHook(webHook).join();
    assertEquals(204, response.statusCode());
  }
}
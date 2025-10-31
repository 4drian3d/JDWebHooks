package io.github._4drian3d.jdwebhooks;

import com.google.gson.JsonParser;
import io.github._4drian3d.jdwebhooks.component.Component;
import io.github._4drian3d.jdwebhooks.component.MediaGalleryComponentImpl;
import io.github._4drian3d.jdwebhooks.component.SeparatorComponentImpl;
import io.github._4drian3d.jdwebhooks.component.TextDisplayComponent;
import io.github._4drian3d.jdwebhooks.webhook.WebHookClientImpl;
import io.github._4drian3d.jdwebhooks.webhook.WebHookImpl;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class WebHookClientTest {
    static WebHookClientImpl client;

    @BeforeAll
    static void beforeAll() {
        client = WebHookClientImpl.fromURL(System.getenv("DISCORD_WEBHOOK_URL"));
    }

    @Test
    void testClientCreation() {
        assertNotNull(client);
    }

    @Test
    void testSuccess() throws ExecutionException, InterruptedException {
        final WebHookImpl webHook = WebHookImpl.builder()
                .content("testSuccess")
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }

    @Test
    void testBasicMessage() throws ExecutionException, InterruptedException, IOException {
        final String username = "testing123";
        final String content = "testBasicMessage";

        final WebHookImpl webHook = WebHookImpl.builder()
                .username(username)
                .content(content)
                .waitForMessage(true)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(200, response.code());

        final var jsonBody = JsonParser.parseString(response.body().string());
        assertTrue(jsonBody.isJsonObject());
        JsonAssertions.assertThatJson(jsonBody).inPath("$.content").isEqualTo(content);
        JsonAssertions.assertThatJson(jsonBody).inPath("$.author.username").isEqualTo(username);
    }

    @Test
    void testTextDisplayComponent() throws ExecutionException, InterruptedException {
        final var component = Component.textDisplay("Text Display Component").build();

        final WebHookImpl webHook = WebHookImpl.builder()
                .component(component)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }

    @Test
    void testSectionComponent() throws ExecutionException, InterruptedException {
        final var textComponents = new ArrayList<TextDisplayComponent>();
        for (int i = 1; i <= 3; i++) {
            textComponents.add(Component.textDisplay("Text Component " + i).build());
        }

        final var avatarUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
        final var accessory = Component.thumbnail(avatarUrl).spoiler(true).description("Hi :)").build();

        final var component = Component.section().components(textComponents).accessory(accessory).build();
        final WebHookImpl webHook = WebHookImpl.builder()
                .component(component)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }

    @Test
    void testMediaGalleryComponent() throws ExecutionException, InterruptedException {
        // generate 10 random image urls
        final var mediaItems = new ArrayList<MediaGalleryComponentImpl.Item>();
        for (int i = 1; i <= 9; i++) {
            final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
            final var mediaItem = MediaGalleryComponentImpl.item(imageUrl).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
            mediaItems.add(mediaItem);
        }

        final var component = Component.mediaGallery().items(mediaItems).build();
        final WebHookImpl webHook = WebHookImpl.builder()
                .component(component)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }

    @Test
    void testSeparatorComponent() throws ExecutionException, InterruptedException {
        final var text1 = Component.textDisplay("Above the separator").build();
        final var text2 = Component.textDisplay("Below the separator").build();
        final var separator = Component.separator().spacing(SeparatorComponentImpl.Spacing.LARGE).build();

        final WebHookImpl webHook = WebHookImpl.builder()
                .components(text1, separator, text2)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }

    @Test
    void testContainerComponent() throws ExecutionException, InterruptedException {
        final var textComponent = Component.textDisplay("Inside Container").build();

        final var mediaItems = new ArrayList<MediaGalleryComponentImpl.Item>();
        for (int i = 1; i <= 9; i++) {
            final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
            final var mediaItem = MediaGalleryComponentImpl.item(imageUrl).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
            mediaItems.add(mediaItem);
        }
        final var mediaComponent = Component.mediaGallery().items(mediaItems).build();

        final var container = Component.container().components(textComponent, mediaComponent).accentColor(0x123456).spoiler(true).build();

        final WebHookImpl webHook = WebHookImpl.builder()
                .component(container)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }
}
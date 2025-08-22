package io.github._4drian3d.jdwebhooks;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.component.*;
import net.javacrumbs.jsonunit.assertj.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

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
    void testSuccess() throws ExecutionException, InterruptedException {
        final WebHook webHook = WebHook.builder()
                .content("testSuccess")
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }

    @Test
    void testBasicMessage() throws ExecutionException, InterruptedException, IOException {
        final String username = "testing123";
        final String content = "testBasicMessage";

        final WebHook webHook = WebHook.builder()
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

        final WebHook webHook = WebHook.builder()
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
        final WebHook webHook = WebHook.builder()
                .component(component)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }

    @Test
    void testMediaGalleryComponent() throws ExecutionException, InterruptedException {
        // generate 10 random image urls
        final var mediaItems = new ArrayList<MediaGalleryComponent.Item>();
        for (int i = 1; i <= 9; i++) {
            final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
            final var mediaItem = MediaGalleryComponent.item(imageUrl).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
            mediaItems.add(mediaItem);
        }

        final var component = Component.mediaGallery().items(mediaItems).build();
        final WebHook webHook = WebHook.builder()
                .component(component)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }

    @Test
    void testFileAttachment() throws ExecutionException, InterruptedException {
        File tempFile;
        try {
            tempFile = File.createTempFile("testfile", ".txt");
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write("This is a test file.");
            }
        } catch (IOException e) {
            fail("Failed to create temporary file: " + e.getMessage());
            return;
        }

        final var attachment = FileAttachment.builder(tempFile).build();

        final WebHook webHook = WebHook.builder()
                .content("Secret message")
                .attachment(attachment)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(200, response.code());
    }

    @Test
    void testFileComponent() throws ExecutionException, InterruptedException {
        File tempFile;
        try {
            tempFile = File.createTempFile("testfile", ".txt");
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write("This is a test file.");
            }
        } catch (IOException e) {
            fail("Failed to create temporary file: " + e.getMessage());
            return;
        }

        final var component = Component.file(tempFile.getName()).build();
        final var attachment = FileAttachment.builder(tempFile).build();

        final WebHook webHook = WebHook.builder()
                .component(component)
                .attachment(attachment)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(200, response.code());
    }

    @Test
    void testSeparatorComponent() throws ExecutionException, InterruptedException {
        final var text1 = Component.textDisplay("Above the separator").build();
        final var text2 = Component.textDisplay("Below the separator").build();
        final var separator = Component.separator().spacing(SeparatorComponent.Spacing.LARGE).build();

        final WebHook webHook = WebHook.builder()
                .components(text1, separator, text2)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }

    @Test
    void testContainerComponent() throws ExecutionException, InterruptedException {
        final var textComponent = Component.textDisplay("Inside Container").build();

        final var mediaItems = new ArrayList<MediaGalleryComponent.Item>();
        for (int i = 1; i <= 9; i++) {
            final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
            final var mediaItem = MediaGalleryComponent.item(imageUrl).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
            mediaItems.add(mediaItem);
        }
        final var mediaComponent = Component.mediaGallery().items(mediaItems).build();

        final var container = Component.container().components(textComponent, mediaComponent).accentColor(0x123456).spoiler(true).build();

        final WebHook webHook = WebHook.builder()
                .component(container)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.code());
    }
}
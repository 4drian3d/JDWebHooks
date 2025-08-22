package io.github._4drian3d.jdwebhooks;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.component.*;
import net.javacrumbs.jsonunit.assertj.*;
import org.junit.jupiter.api.*;

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
        assertEquals(204, response.statusCode());
    }

    @Test
    void testBasicMessage() throws ExecutionException, InterruptedException {
        final String username = "testing123";
        final String content = "testBasicMessage";

        final WebHook webHook = WebHook.builder()
                .username(username)
                .content(content)
                .waitForMessage(true)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(200, response.statusCode());

        final var jsonBody = JsonParser.parseString(response.body());
        assertTrue(jsonBody.isJsonObject());
        JsonAssertions.assertThatJson(jsonBody).inPath("$.content").isEqualTo(content);
        JsonAssertions.assertThatJson(jsonBody).inPath("$.author.username").isEqualTo(username);
    }

    @Test
    void testComponents() throws ExecutionException, InterruptedException {
        final var component = Component.textDisplay().setContent("Text Display Component").build();

        final WebHook webHook = WebHook.builder()
                .component(component)
                .build();

        final var response = client.sendWebHook(webHook).get();
        assertEquals(204, response.statusCode());
    }
}
package io.github._4drian3d.jdwebhooks;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.component.*;
import net.javacrumbs.jsonunit.assertj.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class ComponentSerializerTest {
    private static final Gson gson = GsonProvider.getGson();

    @Test
    void testTextDisplaySerialization() {
        final String content = "Hello, World!";
        final Component component = Component.textDisplay().setContent(content).build();
        final String json = gson.toJson(component);

        // id should be absent since it was not provided
        JsonAssertions.assertThatJson(json).inPath("$.id").isAbsent();
        JsonAssertions.assertThatJson(json).inPath("$.type").isIntegralNumber().isEqualTo(ComponentType.TEXT_DISPLAY.getType());
        JsonAssertions.assertThatJson(json).inPath("$.content").isString().isEqualTo(content);
    }

    @Test
    void testTextDisplaySerializationWithId() {
        final String content = "Hello, World!";
        final int id = 42;
        final Component component = Component.textDisplay().setContent(content).setId(id).build();
        final String json = gson.toJson(component);

        JsonAssertions.assertThatJson(json).inPath("$.id").isIntegralNumber().isEqualTo(id);
        JsonAssertions.assertThatJson(json).inPath("$.type").isIntegralNumber().isEqualTo(ComponentType.TEXT_DISPLAY.getType());
        JsonAssertions.assertThatJson(json).inPath("$.content").isString().isEqualTo(content);
    }

    @Test
    void testSectionSerialization() {
        // generate 3 text display components using a for loop
        final var textComponents = new ArrayList<TextDisplayComponent>();
        for (int i = 1; i <= 3; i++) {
            textComponents.add(Component.textDisplay().setContent("Text Component " + i).build());
        }

        final var avatarUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
        final var accessory = Component.thumbnail().media(avatarUrl).spoiler(true).build();

        final var component = Component.section().components(textComponents).accessory(accessory).build();
        final String json = gson.toJson(component);

        JsonAssertions.assertThatJson(json).inPath("$.id").isAbsent();
        JsonAssertions.assertThatJson(json).inPath("$.type").isIntegralNumber().isEqualTo(ComponentType.SECTION.getType());
        JsonAssertions.assertThatJson(json).inPath("$.components").isArray().hasSize(3);
        for (int i = 0; i < 3; i++) {
            JsonAssertions.assertThatJson(json).inPath("$.components[" + i + "].type").isIntegralNumber().isEqualTo(ComponentType.TEXT_DISPLAY.getType());
            JsonAssertions.assertThatJson(json).inPath("$.components[" + i + "].content").isString().isEqualTo("Text Component " + (i + 1));
        }
        JsonAssertions.assertThatJson(json).inPath("$.accessory").isObject();
        JsonAssertions.assertThatJson(json).inPath("$.accessory.type").isIntegralNumber().isEqualTo(ComponentType.THUMBNAIL.getType());
        JsonAssertions.assertThatJson(json).inPath("$.accessory.media.url").isString().isEqualTo(avatarUrl);
        JsonAssertions.assertThatJson(json).inPath("$.accessory.spoiler").isBoolean().isEqualTo(true);
        JsonAssertions.assertThatJson(json).inPath("$.accessory.description").isAbsent();
    }

    @Test
    void testThumbnailSerialization() {
        final var mediaUrl = "https://example.com/image.png";
        final var description = "An example image";
        final var component = Component.thumbnail().media(mediaUrl).description(description).build();
        final String json = gson.toJson(component);

        JsonAssertions.assertThatJson(json).inPath("$.id").isAbsent();
        JsonAssertions.assertThatJson(json).inPath("$.type").isIntegralNumber().isEqualTo(ComponentType.THUMBNAIL.getType());
        JsonAssertions.assertThatJson(json).inPath("$.media.url").isString().isEqualTo(mediaUrl);
        JsonAssertions.assertThatJson(json).inPath("$.description").isString().isEqualTo(description);
        JsonAssertions.assertThatJson(json).inPath("$.spoiler").isAbsent();
    }
}

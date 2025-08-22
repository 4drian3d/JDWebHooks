package io.github._4drian3d.jdwebhooks;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.component.*;
import io.github._4drian3d.jdwebhooks.serializer.*;
import net.javacrumbs.jsonunit.assertj.*;
import org.junit.jupiter.api.*;

public class ComponentSerializerTest {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(TextDisplayComponent.class, new ComponentSerializer()).create();

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
}

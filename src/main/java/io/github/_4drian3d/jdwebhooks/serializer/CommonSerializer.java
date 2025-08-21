package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

sealed interface CommonSerializer permits EmbedSerializer, WebHookSerializer, AllowedMentionsSerializer {
    default void addNonNull(
            final @NotNull JsonObject object,
            final @NotNull String name,
            final @Nullable Object value
    ) {
        if (value != null) {
            object.addProperty(name, value.toString());
        }
    }

    default void addNonNull(
            final @NotNull JsonObject object,
            final @NotNull String name,
            final @Nullable Number value
    ) {
        if (value != null) {
            object.addProperty(name, value);
        }
    }

    default void addNonNull(
            final @NotNull JsonObject object,
            final @NotNull String name,
            final @Nullable Boolean value
    ) {
        if (value != null) {
            object.addProperty(name, value);
        }
    }
}

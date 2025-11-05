package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.JsonObject;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

sealed interface CommonSerializer permits WebHookSerializer, AllowedMentionsSerializer {
    default void addNonNull(
            final @NonNull JsonObject object,
            final @NonNull String name,
            final @Nullable Object value
    ) {
        if (value != null) {
            object.addProperty(name, value.toString());
        }
    }

    default void addNonNull(
            final @NonNull JsonObject object,
            final @NonNull String name,
            final @Nullable Number value
    ) {
        if (value != null) {
            object.addProperty(name, value);
        }
    }

    default void addNonNull(
            final @NonNull JsonObject object,
            final @NonNull String name,
            final @Nullable Boolean value
    ) {
        if (value != null) {
            object.addProperty(name, value);
        }
    }
}

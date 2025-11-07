package io.github._4drian3d.jdwebhooks.json.deserializer;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.webhook.WebHookData;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Type;

public class WebHookDataDeserializer implements JsonDeserializer<WebHookData> {
  @Override
  public WebHookData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    if (!json.isJsonObject()) {
      throw new JsonParseException("Invalid WebHookData provided: " + json);
    }
    final JsonObject object = json.getAsJsonObject();
    final String avatar = valueOrNull(object, "avatar");
    final String channelId = valueOrNull(object, "channel_id");
    final String guildId = valueOrNull(object, "guild_id");
    final String id = object.getAsJsonPrimitive("id").getAsString();
    final String name = valueOrNull(object, "name");
    final int type = object.getAsJsonPrimitive("type").getAsInt();
    final String token = valueOrNull(object, "token");
    final String url = valueOrNull(object, "url");
    return new WebHookData(id, type, avatar, channelId, guildId, name, token, url);
  }

  @Nullable
  private String valueOrNull(JsonObject object, String key) {
    final JsonElement jsonElement = object.get(key);
    if (jsonElement == null) {
      return null;
    }
    return jsonElement.getAsString();
  }
}

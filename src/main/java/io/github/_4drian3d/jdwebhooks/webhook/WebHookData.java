package io.github._4drian3d.jdwebhooks.webhook;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Used to represent a webhook.
 *
 * @param id the id of the webhook
 * @param type the type of the webhook
 * @param avatar the default user avatar hash of the webhook
 * @param channelId the channel id this webhook is for, if any
 * @param guildId the guild id this webhook is for, if any
 * @param name the default name of the webhook
 * @param token	the secure token of the webhook
 * @param url the url used for executing the webhook
 */
public record WebHookData(
    @NonNull
    String id,
    int type,
    @Nullable
    String avatar,
    @Nullable
    String channelId,
    @Nullable
    String guildId,
    @Nullable
    String name,
    @Nullable
    String token,
    @Nullable
    String url
) {
}

package io.github._4drian3d.jdwebhooks.webhook;

/**
 *
 * @param waitForMessage waits for server confirmation of message send before response,
 *                       and returns the created message body
 * @param threadId Send a message to the specified thread within a webhook's channel.
 *                 The thread will automatically be unarchived.
 */
public record QueryParameters(Boolean waitForMessage, String threadId) {
}

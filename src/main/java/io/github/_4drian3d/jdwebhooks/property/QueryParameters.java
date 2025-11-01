package io.github._4drian3d.jdwebhooks.property;

import org.jspecify.annotations.Nullable;

public sealed interface QueryParameters permits QueryParametersImpl {
  /**
   * Waits for server confirmation of message send before response, and returns the created message body (defaults to false;
   * when false a message that is not saved does not return an error)
   * @return whether to wait for server confirmation
   */
  @Nullable
  Boolean waitForMessage();


  /**
   * Send a message to the specified thread within a webhook's channel. The thread will automatically be unarchived.
   * @return whether to send a message to a specific thread
   */
  @Nullable
  String threadId();

  static Builder builder() {
    return new QueryParametersImpl.BuilderImpl();
  }

  sealed interface Builder permits QueryParametersImpl.BuilderImpl {
    Builder waitForMessage(boolean waitForMessage);

    Builder threadId(String threadId);

    QueryParameters build();
  }
}

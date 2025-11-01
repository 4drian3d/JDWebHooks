package io.github._4drian3d.jdwebhooks.property;

import org.jspecify.annotations.Nullable;

public sealed interface QueryParameters permits QueryParametersImpl {
  /*
   * Sets whether the response should wait for the message to be created.
   *
   * @param waitForMessage the "wait" query parameter
   * @return this builder
   * @see <a href=https://discord.com/developers/docs/resources/webhook#execute-webhook-query-string-params>Execute Webhook Query String Params</a>
   */
  @Nullable
  Boolean waitForMessage();

  /*
   * Sets the ID of an existing thread to send the message in.
   *
   * @param threadId The "thread_id" query parameter
   * @return this builder
   * @see <a href=https://discord.com/developers/docs/resources/webhook#execute-webhook-query-string-params>Execute Webhook Query String Params</a>
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

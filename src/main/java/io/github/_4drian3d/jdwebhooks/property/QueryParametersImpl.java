package io.github._4drian3d.jdwebhooks.property;

import org.jspecify.annotations.Nullable;

record QueryParametersImpl(
    @Nullable Boolean waitForMessage,
    @Nullable String threadId
) implements QueryParameters {
  static final class BuilderImpl implements Builder {
    private Boolean waitForMessage;
    private String threadId;

    @Override
    public Builder waitForMessage(boolean waitForMessage) {
      this.waitForMessage = waitForMessage;
      return this;
    }

    @Override
    public Builder threadId(String threadId) {
      this.threadId = threadId;
      return this;
    }

    @Override
    public QueryParameters build() {
      return new QueryParametersImpl(waitForMessage, threadId);
    }
  }
}

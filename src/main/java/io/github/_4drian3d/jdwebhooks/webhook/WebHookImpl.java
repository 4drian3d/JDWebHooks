package io.github._4drian3d.jdwebhooks.webhook;

import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.component.Component;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Object containing all available items to display in a Discord WebHookImpl.
 *
 * @param username        the username to overwrite the webhook name
 * @param avatarURL       the avatar icon url
 * @param tts
 * @param allowedMentions if this webhook can mention users
 * @param threadName      the thread to be created from this webhook
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
record WebHookImpl(
    @Nullable QueryParameters queryParameters,
    @Nullable String username,
    @Nullable String avatarURL,
    @Nullable Boolean tts,
    @Nullable AllowedMentions allowedMentions,
    @Nullable String threadName,
    @Nullable List<Component> components,
    @Nullable Boolean suppressNotifications
) implements WebHook {

  static final class Builder implements WebHook.Builder {
    private QueryParameters queryParameters;
    private String username;
    private String avatarURL;
    private Boolean tts;
    private AllowedMentions allowedMentions;
    private String threadName;
    private List<Component> components;
    private Boolean suppressNotifications;

    @Override
    @NonNull
    public Builder username(final String username) {
      this.username = requireNonNull(username);
      return this;
    }

    @Override
    @NonNull
    public Builder avatarURL(final String avatarURL) {
      this.avatarURL = avatarURL;
      return this;
    }

    @Override
    @NonNull
    public Builder tts(final Boolean tts) {
      this.tts = tts;
      return this;
    }

    @Override
    @NonNull
    public Builder allowedMentions(final AllowedMentions allowedMentions) {
      this.allowedMentions = allowedMentions;
      return this;
    }

    @Override
    @NonNull
    public Builder threadName(final String threadName) {
      this.threadName = threadName;
      return this;
    }

    @Override
    @NonNull
    public Builder component(final @NonNull Component component) {
      requireNonNull(component);
      if (this.components == null) {
        this.components = new ArrayList<>();
      }
      this.components.add(component);
      return this;
    }

    @Override
    @NonNull
    public Builder components(final @NonNull List<Component> components) {
      requireNonNull(components);
      for (final Component embed : components) {
        this.component(embed);
      }
      return this;
    }

    @Override
    @NonNull
    public Builder components(@NonNull Component @NonNull ... components) {
      requireNonNull(components);
      for (final Component embed : components) {
        this.component(embed);
      }
      return this;
    }

    @Override
    @NonNull
    public Builder suppressNotifications(final @Nullable Boolean suppressNotifications) {
      this.suppressNotifications = suppressNotifications;
      return this;
    }

    @Override
    @NonNull
    public WebHookImpl build() {
      requireNonNull(this.components, "No component provided");
      if (this.components.isEmpty()) {
        throw new IllegalArgumentException("Either content, embeds, or components must be provided");
      }
      return new WebHookImpl(
          this.queryParameters,
          this.username,
          this.avatarURL,
          this.tts,
          this.allowedMentions,
          this.threadName,
          this.components,
          this.suppressNotifications
      );
    }
  }
}

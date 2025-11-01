package io.github._4drian3d.jdwebhooks.webhook;

import io.github._4drian3d.jdwebhooks.component.FileComponent;
import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.component.Component;
import io.github._4drian3d.jdwebhooks.property.QueryParameters;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
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
    @NonNull List<Component> components,
    @Nullable List<FileAttachment> fileAttachments,
    @Nullable Boolean suppressNotifications
) implements WebHook {

  static final class Builder implements WebHook.Builder {
    private QueryParameters queryParameters;
    private String username;
    private String avatarURL;
    private Boolean tts;
    private AllowedMentions allowedMentions;
    private String threadName;
    private final List<Component> components = new ArrayList<>();
    private List<FileAttachment> fileAttachments;
    private Boolean suppressNotifications;

    @Override
    @NonNull
    public Builder queryParameters(@Nullable QueryParameters queryParameters) {
      this.queryParameters = queryParameters;
      return this;
    }

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
      this.components.add(component);
      return this;
    }

    @Override
    @NonNull
    public Builder components(final @NonNull List<Component> components) {
      requireNonNull(components);
      this.components.addAll(components);
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
    public Builder fileAttachment(final @NonNull FileAttachment fileAttachment) {
      if (this.fileAttachments == null) {
        this.fileAttachments = new ArrayList<>();
      }
      this.fileAttachments.add(fileAttachment);
      return this;
    }

    @Override
    @NonNull
    public Builder fileAttachments(final @NonNull List<FileAttachment> fileAttachments) {
      this.fileAttachments = fileAttachments;
      return this;
    }

    @Override
    @NonNull
    public Builder fileAttachments(@NonNull FileAttachment @NonNull... fileAttachments) {
      if (this.fileAttachments == null) {
        this.fileAttachments = new ArrayList<>();
      }
      Collections.addAll(this.fileAttachments, fileAttachments);
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
      if (this.components.isEmpty()) {
        throw new IllegalArgumentException("No component provided");
      }
      for (Component component : List.copyOf(components)) {
        if (component instanceof FileComponent) {
          this.validateFileAttachments(components, fileAttachments);
          break;
        }
      }
      return new WebHookImpl(
          this.queryParameters,
          this.username,
          this.avatarURL,
          this.tts,
          this.allowedMentions,
          this.threadName,
          this.components,
          this.fileAttachments,
          this.suppressNotifications
      );
    }

    private void validateFileAttachments(List<Component> componentList, List<FileAttachment> fileAttachments) {
      if (fileAttachments == null || fileAttachments.isEmpty()) {
        throw new IllegalArgumentException("You need to provide file attachments");
      }

      final List<String> fileComponentsNames = new ArrayList<>();
      final List<String> fileAttachmentNames = new ArrayList<>();

      for (Component component : componentList) {
        if (component instanceof FileComponent fileComponent) {
          fileComponentsNames.add(fileComponent.file().replace(FileAttachment.PREFIX, ""));
        }
      }
      for (FileAttachment fileAttachment : fileAttachments) {
        fileAttachmentNames.add(fileAttachment.filename());
      }
      // Multiple components can reference the same file.
      // However, there cannot be more attachments than file components created.
      if (fileAttachmentNames.size() > fileComponentsNames.size()) {
        throw new IllegalArgumentException("Cannot be more file attachments than file components");
      }

      for (String fileComponentsName : fileComponentsNames) {
        if (!fileAttachmentNames.contains(fileComponentsName)) {
          throw new IllegalArgumentException("No file attachment has been provided for the file component " + fileComponentsName);
        }
      }
    }
  }
}

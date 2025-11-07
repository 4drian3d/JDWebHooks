package io.github._4drian3d.jdwebhooks.component;

import io.github._4drian3d.jdwebhooks.property.AllowedMentions;
import io.github._4drian3d.jdwebhooks.webhook.WebHookExecution;
import org.jspecify.annotations.NullMarked;

/**
 * A Text Display is a content component that allows you to add markdown formatted text,
 * including mentions (users, roles, etc) and emojis.
 * <br>
 * The behavior of this component is extremely similar to the content field of a message,
 * but allows you to add multiple text components, controlling the layout of your message.
 *
 * @apiNote When sent in a message, pingable mentions ({@code @user}, {@code @role}, etc) present in this component
 *  will ping and send notifications based on the value of the {@link io.github._4drian3d.jdwebhooks.property.AllowedMentions} object
 *  set in {@link WebHookExecution.Builder#allowedMentions(AllowedMentions)}.
 */
@NullMarked
public sealed interface TextDisplayComponent extends Component, ContainerableComponent permits TextDisplayComponentImpl {
  /**
   * Text that will be displayed similar to a message
   * @return text that will be displayed
   */
  String content();

  sealed interface Builder extends ComponentBuilder<TextDisplayComponent, Builder> permits TextDisplayComponentImpl.Builder {
    Builder content(String content);
  }
}

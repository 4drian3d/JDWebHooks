package io.github._4drian3d.jdwebhooks.component;

import org.jspecify.annotations.NullMarked;

@NullMarked
public sealed interface TextDisplayComponent extends Component, ContainerableComponent permits TextDisplayComponentImpl {
  String content();

  sealed interface Builder extends ComponentBuilder<TextDisplayComponent, Builder> permits TextDisplayComponentImpl.Builder {
    Builder content(String content);
  }
}

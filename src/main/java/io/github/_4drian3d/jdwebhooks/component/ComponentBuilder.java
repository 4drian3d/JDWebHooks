package io.github._4drian3d.jdwebhooks.component;

public interface ComponentBuilder<R extends Component, T extends ComponentBuilder<R, T>> {
  T id(int id);

  R build();
}

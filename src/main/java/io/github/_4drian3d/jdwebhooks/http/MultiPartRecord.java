package io.github._4drian3d.jdwebhooks.http;

import org.jspecify.annotations.NonNull;

import java.nio.file.Path;

public record MultiPartRecord(
    @NonNull String fieldName,
    String filename,
    Content<?> content
) {
  public sealed interface Content<T> {
    T content();

    record FileContent(Path content) implements Content<Path> {}

    record StringContent(String content) implements Content<String> {}
  }
}

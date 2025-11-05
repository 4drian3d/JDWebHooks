package io.github._4drian3d.jdwebhooks.media;

import org.jspecify.annotations.NullMarked;

import java.net.URI;

import static java.util.Objects.requireNonNull;

/**
 * A reference to a file via an external link
 */
@NullMarked
public sealed interface URLMediaReference extends MediaReference {
  URI uri();

  static URLMediaReference from(URI uri) {
    requireNonNull(uri);
    return new URLMediaReferenceImpl(uri);
  }

  static URLMediaReference from(String uri) {
    requireNonNull(uri);
    return new URLMediaReferenceImpl(URI.create(uri));
  }

  record URLMediaReferenceImpl(URI uri) implements URLMediaReference {
    @Override
    public String mediaReference() {
      return uri.toString();
    }
  }
}

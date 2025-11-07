/**
 * JDWebHooks Main Module
 */
module io.github._4drian3d.jdwebhooks {
  requires com.google.gson;
  requires java.net.http;
  requires static transitive org.jspecify;

  exports io.github._4drian3d.jdwebhooks.component;
  exports io.github._4drian3d.jdwebhooks.webhook;
  exports io.github._4drian3d.jdwebhooks.property;
  exports io.github._4drian3d.jdwebhooks.media;
}
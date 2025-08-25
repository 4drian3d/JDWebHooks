/**
 * JDWebHooks Main Module
 */
module io.github._4drian3d.jdwebhooks {
    requires com.google.gson;
    requires java.net.http;
    requires static org.jetbrains.annotations;
    requires okhttp3;
    requires okio;

    exports io.github._4drian3d.jdwebhooks;
    exports io.github._4drian3d.jdwebhooks.component;
}
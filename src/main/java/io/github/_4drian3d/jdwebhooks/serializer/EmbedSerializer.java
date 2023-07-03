package io.github._4drian3d.jdwebhooks.serializer;

import com.google.gson.*;
import io.github._4drian3d.jdwebhooks.Embed;

import java.lang.reflect.Type;

public final class EmbedSerializer implements JsonSerializer<Embed>, CommonSerializer {
    @Override
    public JsonElement serialize(
            final Embed src,
            final Type typeOfSrc,
            final JsonSerializationContext context
    ) {
        final JsonObject object = new JsonObject();
        this.addNonNull(object, "title", src.title());
        this.addNonNull(object, "type", src.type());
        this.addNonNull(object, "description", src.description());
        this.addNonNull(object, "timestamp", src.timestamp());
        this.addNonNull(object, "color", src.color());
        this.serializeAuthor(object, src.author());
        this.serializeFooter(object, src.footer());
        this.serializeGraphicResource(object, "image", src.image());
        this.serializeGraphicResource(object, "thumbnail", src.thumbnail());
        this.serializeGraphicResource(object, "video", src.video());
        this.serializeProvider(object, src.provider());
        this.serializeFields(object, src.fields());

        return object;
    }

    private void serializeAuthor(final JsonObject object, final Embed.Author author) {
        if (author == null) return;

        final JsonObject authorObject = new JsonObject();
        this.addNonNull(authorObject, "name", author.name());
        this.addNonNull(authorObject, "url", author.url());
        this.addNonNull(authorObject, "icon_url", author.iconURL());
        this.addNonNull(authorObject, "proxy_icon_url", author.proxyIconURL());

        object.add("author", authorObject);
    }

    private void serializeFooter(final JsonObject object, final Embed.Footer footer) {
        if (footer == null) return;

        final JsonObject footerObject = new JsonObject();
        this.addNonNull(footerObject, "text", footer.text());
        this.addNonNull(footerObject, "icon_url", footer.iconURL());
        this.addNonNull(footerObject, "proxy_icon_url", footer.proxyIconURL());

        object.add("footer", footerObject);
    }

    private void serializeGraphicResource(
            final JsonObject object,
            final String name,
            final Embed.GraphicResource resource
    ) {
        if (resource == null) return;

        final JsonObject imageObject = new JsonObject();
        this.addNonNull(imageObject, "url", resource.url());
        this.addNonNull(imageObject, "proxy_url", resource.proxyURL());
        this.addNonNull(imageObject, "height", resource.height());
        this.addNonNull(imageObject, "width", resource.width());

        object.add(name, imageObject);
    }

    private void serializeProvider(final JsonObject object, final Embed.Provider provider) {
        if (provider == null) return;

        final JsonObject providerObject = new JsonObject();
        this.addNonNull(providerObject, "name", provider.name());
        this.addNonNull(providerObject, "url", provider.url());

        object.add("provider", providerObject);
    }

    private void serializeFields(final JsonObject object, final Embed.Field[] fields) {
        if (fields == null) return;

        final JsonArray fieldArray = new JsonArray();

        for (final Embed.Field field : fields) {
            final JsonObject fieldObject = new JsonObject();
            this.addNonNull(fieldObject, "inline", field.inline());
            fieldObject.addProperty("name", field.name());
            fieldObject.addProperty("value", field.value());
            fieldArray.add(fieldObject);
        }

        object.add("fields", fieldArray);
    }
}

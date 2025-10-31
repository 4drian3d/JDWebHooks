# JDWebHooks

A library to send Discord WebHooks easily

## Discord Compatibility

The following API calls are supported:

- [x] [Execute Webhook](https://discord.com/developers/docs/resources/webhook#execute-webhook) (sending message)
    - [x] `wait` query parameter
    - [x] `thread_id` query parameter
    - [x] `with_components` query parameter (cannot be set manually, it's set automatically when components are used)
    - [x] `content` field
    - [x] `username` field
    - [x] `avatar_url` field
    - [x] `tts` field
    - [x] `embeds` field (up to 10 embeds)
    - [x] `allowed_mentions` field
    - [x] `components` field (only Components v2)
    - [x] `files[n]` field
    - [x] `attachments` field
    - [x] `flags` field
    - [x] `thread_name` field
    - [ ] `applied_tags` field
    - [ ] `poll` field
- [ ] [Get Webhook Message](https://discord.com/developers/docs/resources/webhook#get-webhook-message)
    - [ ] `thread_id` query parameter
- [ ] [Edit Webhook Message](https://discord.com/developers/docs/resources/webhook#edit-webhook-message)
    - [ ] `thread_id` query parameter
    - [ ] `with_commponents` query parameter
    - [ ] `content` field
    - [ ] `embeds` field (up to 10 embeds)
    - [ ] `flags` field
    - [ ] `allowed_mentions` field
    - [ ] `components` field (only Components v2)
    - [ ] `files[n]` field
    - [ ] `attachments` field
- [ ] [Delete Webhook Message](https://discord.com/developers/docs/resources/webhook#delete-webhook-message)
    - [ ] `thread_id` query parameter
- [ ] [Get Webhook with Token](https://discord.com/developers/docs/resources/webhook#get-webhook-with-token)
- [ ] [Modify Webhook with Token](https://discord.com/developers/docs/resources/webhook#modify-webhook-with-token)
    - [ ] `name` field
    - [ ] `avatar` field
- [ ] [Delete Webhook with Token](https://discord.com/developers/docs/resources/webhook#delete-webhook-with-token)

## Requirements
- Java 17
- OkHttp 5.1.0 to be included in the classpath (it's not shaded in the jar)

## Usage

### Dependency
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.4drian3d:jdwebhooks:1.0.1")
}
```

### Examples

#### Basic Example

```java

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        final WebHookClient client = WebHookClient.from("id", "token"); // or WebHookClient.from(url);

        final WebHook webHook = WebHook.builder()
                .username("4drian3d was here")
                .embed(embed)
                .build();

        final CompletableFuture<HttpResponse<String>> futureResponse = client.sendWebHook(webHook);
    }
}

```

![](https://github.com/4drian3d/JDWebHooks/assets/68704415/e0950431-24c6-42b7-9f3b-302a7e7be8ef)

#### With Attachments

```java
File file; // get your file from somewhere

final var attachment = FileAttachment.builder(file).build();

final WebHook webHook = WebHook.builder()
        .content("Secret file")
        .attachment(attachment)
        .build();
```

#### Text Display Component

```java
final var component = Component.textDisplay("Text Display Component").build();

final WebHook webHook = WebHook.builder()
        .component(component)
        .build();
```

#### Section Component

```java
final var textComponents = new ArrayList<TextDisplayComponent>();
for(
int i = 1;
i <=3;i++){
        textComponents.

add(Component.textDisplay("Text Component "+i).

build());
        }

final var avatarUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
final var accessory = Component.thumbnail(avatarUrl).spoiler(true).description("Hi :)").build();

final var component = Component.section().components(textComponents).accessory(accessory).build();
final WebHook webHook = WebHook.builder()
        .component(component)
        .build();
```

#### Media Gallery Component

```java
final var mediaItems = new ArrayList<MediaGalleryComponent.Item>();
for(
int i = 1;
i <=9;i++){
final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
final var mediaItem = MediaGalleryComponent.item(imageUrl).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
    mediaItems.

add(mediaItem);
}

final var component = Component.mediaGallery().items(mediaItems).build();
final WebHook webHook = WebHook.builder()
        .component(component)
        .build();
```

#### File Component

```java
File file; // get your file from somewhere

final var component = Component.file(file.getName()).build(); // the file component only needs the name, you have to upload the actual file via an attachment
final var attachment = FileAttachment.builder(file).build();

final WebHook webHook = WebHook.builder()
        .component(component)
        .attachment(attachment)
        .build();
```

#### Separator Component

```java
final var text1 = Component.textDisplay("Above the separator").build();
final var text2 = Component.textDisplay("Below the separator").build();
final var separator = Component.separator().spacing(SeparatorComponent.Spacing.LARGE).build();

final WebHook webHook = WebHook.builder()
        .components(text1, separator, text2)
        .build();
```

#### Container Component

```java
final var textComponent = Component.textDisplay("Inside Container").build();

final var mediaItems = new ArrayList<MediaGalleryComponent.Item>();
for(
int i = 1;
i <=9;i++){
final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
final var mediaItem = MediaGalleryComponent.item(imageUrl).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
    mediaItems.

add(mediaItem);
}
final var mediaComponent = Component.mediaGallery().items(mediaItems).build();

final var container = Component.container().components(textComponent, mediaComponent).accentColor(0x123456).spoiler(true).build();

final WebHook webHook = WebHook.builder()
        .component(container)
        .build();
```
## Testing

There are unit tests included in the project using JUnit 5 and JsonUnit.

To run the tests, you must first set the DISCORD_WEBHOOK_URL environment variable with a valid Discord WebHook URL.

Then, you can run the `test` task using your IDE or build tool.



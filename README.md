# JDWebHooks

A library to send Discord WebHooks with Components V2 support easily

## Discord Compatibility

The following API calls are supported:

- [x] [Execute Webhook](https://discord.com/developers/docs/resources/webhook#execute-webhook)
(Only non-interactive components supported)
- [ ] [Get Webhook Message](https://discord.com/developers/docs/resources/webhook#get-webhook-message)
- [ ] [Edit Webhook Message](https://discord.com/developers/docs/resources/webhook#edit-webhook-message)
- [ ] [Delete Webhook Message](https://discord.com/developers/docs/resources/webhook#delete-webhook-message)
- [ ] [Get Webhook with Token](https://discord.com/developers/docs/resources/webhook#get-webhook-with-token)
- [ ] [Modify Webhook with Token](https://discord.com/developers/docs/resources/webhook#modify-webhook-with-token)
- [ ] [Delete Webhook with Token](https://discord.com/developers/docs/resources/webhook#delete-webhook-with-token)

## Requirements
- Java 21

## Usage

### Dependency
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.4drian3d:jdwebhooks:2.0.0")
}
```

### Examples

#### Basic Example

```java

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

void main() {
  final WebHookClient client = WebHookClient.from("id", "token"); // or WebHookClient.from(url);

  final WebHookExecution webHook = WebHookExecution.builder()
      .username("4drian3d was here")
      .component(
          Component.container()
              .components(
                  Component.textDisplay("4drian3d"),
                  Component.textDisplay("**My first Discord WebHook**")
              )
              .accentColor(0xFF0000)
      )
      .build();

  final CompletableFuture<HttpResponse<String>> futureResponse = client.sendWebHook(webHook);
  // If your application only executes the webhook, you should use the CompletableFuture#join()
  // method to prevent the application from terminating before the webhook is sent.
  // And if you want to obtain the result of the webhook, you should use CompletableFuture#thenAccept()
}

```

![](https://github.com/4drian3d/JDWebHooks/assets/68704415/e0950431-24c6-42b7-9f3b-302a7e7be8ef)

#### Text Display Component

```java
final Component component = Component.textDisplay("Text Display Component");

final WebHookExecution webHook = WebHookExecution.builder()
        .component(component)
        .build();
```

#### Section Component

```java
final List<TextDisplayComponent> textComponents = new ArrayList<>();
for (int i = 1; i <=3; i++) {
  textComponents.add(Component.textDisplay("Text Component "+i).build());
}

final Component avatarUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
final Component accessory = Component.thumbnail().media(URLMediaReference.from(avatarUrl)).spoiler(true).description("Hi :)").build();

final Component component = Component.section().components(textComponents).accessory(accessory).build();
final WebHookExecution webHook = WebHookExecution.builder()
        .component(component)
        .build();
```

#### Media Gallery Component

```java
final List<MediaGalleryComponent.Item> mediaItems = new ArrayList<>();
for (int i = 1; i <= 9; i++) {
  final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
  final var mediaItem = MediaGalleryComponent.item(imageUrl).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
  mediaItems.add(mediaItem);
}

final Component component = Component.mediaGallery().items(mediaItems).build();
final WebHookExecution webHook = WebHookExecution.builder()
        .component(component)
        .build();
```

#### File Component

```java
Path file = Path.of("build.gradle.kts"); // get your file from somewhere

final FileAttachment attachment = FileAttachment.fromFile(file);

final WebHookExecution webHook = WebHookExecution.builder()
    .components(
        Component.textDisplay("Secret file"),
        Component.file().file(attachment).spoiler(true).build()
    )
    .attachment(attachment)
    .build();
```

#### Separator Component

```java
final Component text1 = Component.textDisplay("Above the separator");
final Component text2 = Component.textDisplay("Below the separator");
final Component separator = Component.separator().spacing(SeparatorComponent.Spacing.LARGE).build();

final WebHookExecution webHook = WebHookExecution.builder()
        .components(text1, separator, text2)
        .build();
```

#### Container Component

```java
final Component textComponent = Component.textDisplay("Inside Container").build();

final List<MediaGalleryComponent.Item> mediaItems = new ArrayList<>();
for (int i = 1; i <=9; i++) {
    final var imageUrl = "https://api.dicebear.com/9.x/bottts/png?seed=" + UUID.randomUUID();
    final var mediaItem = MediaGalleryComponent.itemBuilder().media(URLMediaReference.from(imageUrl)).description("Image " + i).spoiler((i - 1) % 2 == 0).build();
    mediaItems.add(mediaItem);
}
final Component mediaComponent = Component.mediaGallery().items(mediaItems).build();

final Component container = Component.container().components(textComponent, mediaComponent).accentColor(0x123456).spoiler(true).build();

final WebHookExecution webHook = WebHookExecution.builder()
        .component(container)
        .build();
```

## Testing

There are unit tests included in the project using JUnit 5 and JsonUnit.

To run the tests, you must first set the DISCORD_WEBHOOK_URL environment variable with a valid Discord WebHook URL.

Then, you can run the `test` task using your IDE or build tool.



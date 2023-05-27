# JDWebHooks

A library to send Discord WebHooks easily

## Requirements
- Java 17

## Usage

### Dependency
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.4drian3d:jdwebhooks:1.0.0")
}
```

### Example

```java

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        final WebHookClient client = WebHookClient.from("id", "token");
        final Embed embed = Embed.builder()
                .author(Embed.Author.builder().name("4drian3d").build())
                .timestamp(Instant.now())
                .color(0xFF0000)
                .title("My first Discord WebHook")
                .build();

        final WebHook webHook = WebHook.builder()
                .username("4drian3d was here")
                .embed(embed)
                .build();

        final CompletableFuture<HttpResponse<String>> futureResponse = client.sendWebHook(webHook);
    }
}

```

![](https://github.com/4drian3d/JDWebHooks/assets/68704415/e0950431-24c6-42b7-9f3b-302a7e7be8ef)

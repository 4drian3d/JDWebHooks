plugins {
    `java-library`
    id("com.vanniktech.maven.publish") version "0.34.0"
}

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnlyApi(libs.annotations.jetbrains)
    compileOnlyApi(libs.annotations.jspecify)
    implementation(libs.gson)
    implementation(libs.okhttp)

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.0")
    testImplementation("org.junit.platform:junit-platform-launcher:1.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")

    // JsonUnit
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:4.1.1")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
        options.compilerArgs.add("-Xlint:-processing")
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).links(
            "https://docs.oracle.com/en/java/javase/21/docs/api/",
            "https://www.javadocs.dev/com.google.code.gson/gson/${libs.versions.gson.get()}",
            "https://www.javadocs.dev/org.jetbrains/annotations/${libs.versions.annotations.jetbrains.get()}"
        )
    }
    test {
        useJUnitPlatform()
        environment("DISCORD_WEBHOOK_URL", System.getenv("DISCORD_WEBHOOK_URL"))
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates(project.group as String, "jdwebhooks", project.version as String)

    pom {
        url.set("https://github.com/4drian3d/JDWebHooks")
        licenses {
            license {
                name.set("Apache License, Version 2.0")
                url.set("https://opensource.org/licenses/Apache-2.0")
            }
        }
        scm {
            connection.set("scm:git:https://github.com/4drian3d/JDWebHooks.git")
            developerConnection.set("scm:git:ssh://git@github.com/4drian3d/JDWebHooks.git")
            url.set("https://github.com/4drian3d/JDWebHooks")
        }
        developers {
            developer {
                id.set("4drian3d")
                name.set("Adrian Gonzales")
                email.set("adriangonzalesval@gmail.com")
            }
        }
        issueManagement {
            name.set("GitHub")
            url.set("https://github.com/4drian3d/JDWebHooks/issues")
        }
        ciManagement {
            name.set("GitHub Actions")
            url.set("https://github.com/4drian3d/JDWebHooks/actions")
        }
        name.set(project.name)
        description.set(project.description)
        url.set("https://github.com/4drian3d/JDWebHooks")
    }
}

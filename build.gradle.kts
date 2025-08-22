plugins {
    `java-library`
    `maven-publish`
    signing
}

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnlyApi(libs.annotations)
    implementation(libs.gson)

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
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).links(
                "https://docs.oracle.com/en/java/javase/17/docs/api/",
                "https://javadoc.io/doc/com.google.code.gson/gson",
                "https://javadoc.io/doc/org.jetbrains/annotations"
        )
    }
    test {
        useJUnitPlatform()
        environment("DISCORD_WEBHOOK_URL", System.getenv("DISCORD_WEBHOOK_URL"))
    }
}

//publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            repositories {
//                maven {
//                    credentials(PasswordCredentials::class)
//                    val central = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
//                    val snapshots = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
//                    if (project.version.toString().endsWith("SNAPSHOT")) {
//                        name = "SonatypeSnapshots"
//                        setUrl(snapshots)
//                    } else {
//                        name = "OSSRH"
//                        setUrl(central)
//                    }
//                }
//            }
//            from(components["java"])
//            pom {
//                url.set("https://github.com/4drian3d/JDWebHooks")
//                licenses {
//                    license {
//                        name.set("Apache License, Version 2.0")
//                        url.set("https://opensource.org/licenses/Apache-2.0")
//                    }
//                }
//                scm {
//                    connection.set("scm:git:https://github.com/4drian3d/JDWebHooks.git")
//                    developerConnection.set("scm:git:ssh://git@github.com/4drian3d/JDWebHooks.git")
//                    url.set("https://github.com/4drian3d/JDWebHooks")
//                }
//                developers {
//                    developer {
//                        id.set("4drian3d")
//                        name.set("Adrian Gonzales")
//                        email.set("adriangonzalesval@gmail.com")
//                    }
//                }
//                issueManagement {
//                    name.set("GitHub")
//                    url.set("https://github.com/4drian3d/JDWebHooks/issues")
//                }
//                ciManagement {
//                    name.set("GitHub Actions")
//                    url.set("https://github.com/4drian3d/JDWebHooks/actions")
//                }
//                name.set(project.name)
//                description.set(project.description)
//                url.set("https://github.com/4drian3d/JDWebHooks")
//            }
//            artifactId = "jdwebhooks"
//        }
//    }
//}
//signing {
//    useGpgCmd()
//    sign(configurations.archives.get())
//    sign(publishing.publications["maven"])
//}

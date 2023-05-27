plugins {
    `java-library`
    alias(libs.plugins.indra)
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
}

indra {
    javaVersions {
        testWith().add(17)
    }
    github("4drian3d", "JDWebHooks") {
        ci(true)
    }
    publishReleasesTo("OSSRH", "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
    publishSnapshotsTo("SonatypeSnapshots",  "https://s01.oss.sonatype.org/content/repositories/snapshots/")
    gpl3OrLaterLicense()
    configurePublications {
        artifactId = "jdwebhooks"
        from(components["java"])

        pom {
            developers {
                developer {
                    id.set("4drian3d")
                    name.set("Adrian Gonzales")
                    email.set("adriangonzalesval@gmail.com")
                }
            }
        }
    }
}
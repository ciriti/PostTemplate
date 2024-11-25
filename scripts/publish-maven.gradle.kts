plugins {
    `maven-publish`
    signing
    alias(libs.plugins.dokka)
}

tasks {
    register<Jar>("androidSourcesJar") {
        archiveClassifier.set("sources")
        from(android.sourceSets["main"].java.srcDirs)
        from(android.sourceSets["main"].kotlin.srcDirs)
    }

    register<Jar>("javadocJar") {
        dependsOn("dokkaJavadoc")
        archiveClassifier.set("javadoc")
        from(tasks["dokkaJavadoc"])
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["release"])

            artifact(tasks["androidSourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set(project.name)
                description.set("Description for ${project.name}")
                url.set("https://github.com/YourUser/YourRepository")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("YourID")
                        name.set("Your Name")
                        email.set("Your Email")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/YourUser/YourRepository.git")
                    developerConnection.set("scm:git:ssh://github.com/YourUser/YourRepository.git")
                    url.set("https://github.com/YourUser/YourRepository")
                }
            }
        }
    }

    repositories {
        maven {
            name = "sonatype"
            url = uri(
                if (project.version.toString().endsWith("SNAPSHOT"))
                    "https://oss.sonatype.org/content/repositories/snapshots/"
                else
                    "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
            )

            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("SIGNING_KEY_ID"),
        System.getenv("SIGNING_PRIVATE_KEY"),
        System.getenv("SIGNING_PASSWORD")
    )
    sign(publishing.publications["release"])
}

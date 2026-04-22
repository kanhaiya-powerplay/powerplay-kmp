plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
  //  alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.skie) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
}

subprojects {
    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        apply(plugin = "maven-publish")
        
        group = "com.company.powerplay"
        version = System.getenv("VERSION") ?: "local"

        configure<PublishingExtension> {
            repositories {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/kanhaiya-powerplay/powerplay-kmp")

                    credentials {
                        username = System.getenv("GITHUB_ACTOR") ?: "kanhaiya-powerplay"
                        password = System.getenv("GITHUB_TOKEN")
                    }
                }
            }
        }
    }
}
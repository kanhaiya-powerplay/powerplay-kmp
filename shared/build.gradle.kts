import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.skie)
    alias(libs.plugins.serialization)
}

kotlin {
//    androidTarget {
//        publishLibraryVariants("release")
//        compilerOptions {
//            jvmTarget.set(JvmTarget.JVM_11)
//        }
//    }

    androidLibrary {
        namespace = "com.example.powerplaykmp.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    val xcFrameworkName = "XCPowerplayKMP"
    val xcFramework = XCFramework(xcFrameworkName)

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcFrameworkName
          //  binaryOption("bundleID", "org.example.${xcFrameworkName}")
            freeCompilerArgs += "-Xbinary=bundleId=com.powerplay.kmp"
            xcFramework.add(this)
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(project(":network"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

publishing {
    repositories {
        group = "com.company.powerplay"
        version = System.getenv("VERSION") ?: "local"
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Deepakgoyal-iOS/powerplay_kmp")
            credentials {
                username = "Deepakgoyal-iOS"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

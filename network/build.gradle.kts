plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.serialization)

}

kotlin {

    androidLibrary {
        namespace = "com.example.network"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }


    iosX64()
    iosArm64()
    iosSimulatorArm64()


    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.bundles.ktor)
            }
        }


        androidMain {
            dependencies {
                implementation(libs.android.ktor)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ios.ktor)
            }
        }
    }

}

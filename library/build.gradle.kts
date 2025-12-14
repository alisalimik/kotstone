plugins {
    alias(libs.plugins.kapstone.project)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        getByName("jvmAndAndroidMain").dependencies {
            implementation(libs.java.jna)
        }
    }
}

android {
    namespace = "ca.moheektech.kapstone"
    compileSdk = 36

    sourceSets {
        sourceSets["main"].jniLibs.srcDir("interop/linked-android")
    }
}

dependencies {
    dokkaPlugin(libs.dokka.android)
    dokkaHtmlPlugin(libs.dokka.versioning)
}
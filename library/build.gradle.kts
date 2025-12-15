plugins { alias(libs.plugins.kapstone.project) }

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

    jvmMain { dependencies { implementation(libs.java.jna) } }

    androidInstrumentedTest {
      dependencies {
        implementation(libs.androidx.test.junit)
        implementation(libs.androidx.test.espresso)
        implementation(libs.androidx.test.runner)
      }
    }
  }
}

android {
  namespace = "ca.moheektech.kapstone"
  compileSdk = 36

  defaultConfig {
    minSdk = 21
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  sourceSets { sourceSets["main"].jniLibs.srcDir("interop/linked-android") }
}

dependencies {
  dokkaPlugin(libs.dokka.android)
  dokkaHtmlPlugin(libs.dokka.versioning)
  implementation(libs.java.jna) { artifact { type = "aar" } }
}

plugins { alias(libs.plugins.kapstone.project) }

kotlin {
  androidComponents.onVariants {
    it.sources.jniLibs?.addStaticSourceDirectory("interop/linked-android")
  }

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlin.stdlib)
      implementation(libs.kotlinx.coroutines.core)
    }

    commonTest.dependencies {
      implementation(libs.kotlin.test)
      implementation(libs.kotlinx.coroutines.test)
    }

    jsMain.dependencies { implementation(libs.kotlin.stdlib.js) }

    wasmJsMain.dependencies { implementation(libs.kotlin.stdlib.wasm) }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
      browser {
        testTask {
          useKarma {
            useChromeHeadless()
            useConfigDirectory(project.file("karma.config.d"))
          }
        }
      }
    }

    androidDeviceTest.dependencies {
      implementation(libs.androidx.test.junit)
      implementation(libs.androidx.test.espresso)
      implementation(libs.androidx.test.runner)
    }

    val jna =
        create("jnaMain") {
          dependsOn(commonMain.get())
          dependsOn(getByName("nonNativeMain"))
        }

    jvmMain {
      dependsOn(jna)
      dependencies { implementation(libs.java.jna) }
    }

    androidMain {
      dependsOn(jna)
      dependencies { implementation(libs.java.jna.get().apply { artifact { type = "aar" } }) }
    }
  }
}

dependencies {
  dokkaPlugin(libs.dokka.android)
  dokkaHtmlPlugin(libs.dokka.versioning)
}

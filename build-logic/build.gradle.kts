plugins {
    `kotlin-dsl`
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xcontext-parameters")
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.kotlin.gradle.ExperimentalWasmDsl")
                optIn("org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi")
            }
        }
    }
}

gradlePlugin {
    plugins {
        register("kapstone-project") {
            id = "kapstone.project"
            implementationClass = "KapstoneProjectPlugin"
        }
    }
}

dependencies {
    implementation(libs.kotlin.multiplatform)
    implementation(libs.maven.publish)
    implementation(libs.ktfmt.gradle)
    implementation(libs.dokka.gradle)
    implementation(libs.android.gradle)
}
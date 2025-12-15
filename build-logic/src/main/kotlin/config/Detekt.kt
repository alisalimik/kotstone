package config

import io.gitlab.arturbosch.detekt.extensions.DetektExtension

fun DetektExtension.configureDetekt() {
    buildUponDefaultConfig = true
    allRules = false
}
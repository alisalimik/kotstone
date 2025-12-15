package config

import kotlinx.validation.ApiValidationExtension

@OptIn(kotlinx.validation.ExperimentalBCVApi::class)
fun ApiValidationExtension.configureCompatibility() {

    klib {
        enabled = true
    }

    apiDumpDirectory = "aux/validation"
}
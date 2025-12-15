package platform

import org.gradle.api.Project

/**
 * Extension property to get ToolchainProviders from a Project.
 * Usage: project.toolchains.hasZig.get()
 */
val Project.toolchains: ToolchainProviders
    get() = extensions.findByType(ToolchainProviders::class.java) ?: extensions.create(
        "toolchains",
        ToolchainProviders::class.java,
        this
    )


/**
 * Configuration cache-compatible helper to get toolchain boolean value.
 * This evaluates the provider but allows Gradle to cache the result.
 */
internal fun Project.getToolchainBoolean(provider: org.gradle.api.provider.Provider<Boolean>): Boolean {
    return provider.get()
}
package platform.sources

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters

/**
 * Configuration cache-compatible value source for checking command existence.
 * This executes the command check only when needed and caches the result.
 */
abstract class CommandExistsValueSource :
    ValueSource<Boolean, CommandExistsValueSource.Parameters> {
    interface Parameters : ValueSourceParameters {
        val command: Property<String>
        val args: ListProperty<String>
    }

    override fun obtain(): Boolean {
        val cmd = parameters.command.get()
        val args = parameters.args.getOrElse(listOf("--version"))

        return runCatching {
            ProcessBuilder(cmd, *args.toTypedArray())
                .redirectErrorStream(true)
                .start()
                .waitFor() == 0
        }.getOrDefault(false)
    }
}
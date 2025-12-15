package external.tasks

import external.buildCapstoneForTarget
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Task to build Capstone for a specific target
 */
abstract class BuildCapstoneTask : DefaultTask() {
    @get:Input
    var targetName: String = ""

    @TaskAction
    fun buildCapstone() {
        buildCapstoneForTarget(project, targetName)
    }
}

package config

import org.gradle.api.Project
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.dokka.gradle.engine.plugins.DokkaHtmlPluginParameters


fun Project.configureDokka() {
    plugins.withId("org.jetbrains.dokka") {
        extensions.configure(DokkaExtension::class.java) {

            dokkaPublications.named("html") {
                suppressInheritedMembers.set(true)
            }

            dokkaSourceSets.all {
                documentedVisibilities.add(VisibilityModifier.Public)
                sourceLink {
                    localDirectory.set(project.file("src/$name/kotlin"))
                    remoteUrl(getGithubLink())
                    remoteLineSuffix.set("#L")
                }
            }

            pluginsConfiguration.named("html", DokkaHtmlPluginParameters::class.java) {
                footerMessage.set("(c) 2025 ${getAuthor()}")
            }
        }
    }
}
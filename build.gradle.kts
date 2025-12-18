
tasks.register("capstoneBuildAll") {
    group = "capstone"
    description = "Builds all Capstone libraries (delegates to :library:capstoneBuildAll)"
    dependsOn(":library:capstoneBuildAll")
}
package external

data class CapstoneBuildConfig(
    val targetName: String,
    val cmakeToolchainFile: String? = null,
    val cmakeSystemName: String? = null,
    val cmakeSystemProcessor: String? = null,
    val cCompiler: String? = null,
    val cxxCompiler: String? = null,
    val archFlags: List<String> = emptyList(),
    val additionalCMakeArgs: List<String> = emptyList(),
    val enabled: Boolean = true,
    val buildShared: Boolean = false,
    val reuseOutputFrom: String? = null
)

import org.gradle.api.file.*
import org.gradle.api.provider.*
import org.gradle.api.tasks.*
import org.gradle.internal.impldep.org.apache.sshd.common.Property.StringProperty
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import org.jetbrains.kotlin.gradle.targets.native.toolchain.*
import org.jetbrains.kotlin.konan.target.*
import javax.inject.Inject
import kotlin.reflect.*

@Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")
abstract class KonanArTask @Inject constructor(
    @get:Input val konanTarget: KonanTarget
) : AbstractExecTask<KonanArTask>(KonanArTask::class.java), UsesKotlinNativeBundleBuildService {

    @get:Nested
    internal val kotlinNativeProvider: Provider<KotlinNativeFromToolchainProvider> = project.provider {
        KotlinNativeFromToolchainProvider(project, konanTarget, kotlinNativeBundleBuildService)
    }

    @get:OutputDirectory
    val outputDirectory: DirectoryProperty = objectFactory.directoryProperty().convention(
        project.layout.dir(
            project.provider {
                temporaryDirFactory.create()!!
            }
        )
    )

    @get:InputFiles
    val files: ConfigurableFileCollection = objectFactory.fileCollection()

    @get:Input
    val outputFileName: Property<String> = objectFactory.property<String>()

    final override fun exec() {
        val provider = kotlinNativeProvider.get()

        outputDirectory.get().asFile.apply {
            deleteRecursively()
            mkdirs()
        }

        workingDir(outputDirectory.get())

        executable(provider.bundleDirectory.file("bin/run_konan").get().asFile.absolutePath)
        args("llvm", "llvm-ar", "r", outputFileName.get())

        args(files.map { it.absolutePath })

        super.exec()
    }
}

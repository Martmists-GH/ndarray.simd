import org.gradle.api.file.*
import org.gradle.api.provider.*
import org.gradle.api.tasks.*
import org.jetbrains.kotlin.gradle.targets.native.toolchain.*
import org.jetbrains.kotlin.konan.target.*
import java.io.File
import javax.inject.Inject
import kotlin.reflect.*

@Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")
abstract class KonanCompileTask @Inject constructor(
    @get:Input val konanTarget: KonanTarget
) : AbstractExecTask<KonanCompileTask>(KonanCompileTask::class.java), UsesKotlinNativeBundleBuildService {

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
    val arguments: ListProperty<String> = objectFactory.listProperty(String::class.java)

    final override fun exec() {
        val provider = kotlinNativeProvider.get()

        outputDirectory.get().asFile.apply {
            deleteRecursively()
            mkdirs()
        }

        workingDir(outputDirectory.get())

        executable(File(provider.bundleDirectory.get()).resolve("bin/run_konan").absolutePath)
        args("clang", "clang", konanTarget)

        args(files.map { it.absolutePath })

        args(arguments.get())

        super.exec()
    }
}

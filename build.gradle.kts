import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.CInteropProcess
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile
import org.jetbrains.kotlin.tooling.core.emptyExtras

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = "com.martmists.ndarray-simd"
version = "1.1.1"
val isProduction = (findProperty("production") ?: System.getProperty("production")) != null

repositories {
    mavenCentral()
}

kotlin {
    withSourcesJar()

    jvm()

    val natives = if (isProduction) {
        listOf(
            linuxX64(),
            linuxArm64(),
            mingwX64(),
//            mingwArm64(),

            // I can't be bothered to fix these two tbh
            // Feel free to open a PR
//            macosX64(),
//            macosArm64(),
        )
    } else {
        when (val osArch = System.getProperty("os.arch")) {
            "amd64", "x86_64" -> when (val osName = System.getProperty("os.name")) {
                "Linux" -> listOf(linuxX64())
                "Windows" -> listOf(mingwX64())
                "Mac OS X" -> listOf(macosX64())
                else -> error("Unsupported OS: $osName")
            }
            "arm64", "aarch64" -> when (val osName = System.getProperty("os.name")) {
                "Linux" -> listOf(linuxArm64())
                "Windows" -> error("Unsupported OS: Windows on ARM")
                "Mac OS X" -> listOf(macosArm64())
                else -> error("Unsupported OS: $osName")
            }
            else -> error("Unsupported architecture: $osArch")
        }
    }

    for (native in natives) {
        native.apply {
            binaries {
                sharedLib {
                    baseName = "ndarray_simd"
                }
            }

            compilations.named("main") {
                val jni by cinterops.creating {
                    val javaHome = File(System.getProperty("java.home")!!)
                    defFile(projectDir.resolve("src/nativeMain/cinterops/jni.def"))
                    includeDirs(
                        javaHome.resolve("include"),
                        javaHome.resolve("include/linux"),
                        javaHome.resolve("include/darwin"),
                        javaHome.resolve("include/win32"),
                    )
                }

                val simd by cinterops.creating {
                    defFile(projectDir.resolve("src/nativeMain/cinterops/simd.def"))
                    includeDirs(
                        projectDir.resolve("src/lib"),
                    )

                    extraOpts("-Xsource-compiler-option", "-std=c++20")
                    extraOpts("-Xsource-compiler-option", "-O2")

                    val cppSource = projectDir.resolve("src/lib/cpp").listFiles().filter { it.extension == "cpp" }.map { it.absolutePath }
                    cppSource.forEach {
                        extraOpts("-Xcompile-source", it)
                    }

                    val includes = listOf("src/lib/public", "xsimd/include")
                    includes.forEach {
                        extraOpts("-Xsource-compiler-option", "-I${projectDir.resolve(it).absolutePath}")
                    }

                    val extraFlags = arrayOf(
                        "-I/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/usr/include/c++/v1/",
                        "-I/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/usr/include/",
                        "-I~/.konan/dependencies/apple-llvm-20200714-macos-${if (System.getProperty("os.arch") in arrayOf("amd64", "x86_64")) "x64" else "aarch64"}-essentials/lib/clang/11.1.0/include/",
                    )

                    if (target.name.startsWith("macos")) {
                        for (extraFlag in extraFlags) {
                            extraOpts("-Xsource-compiler-option", extraFlag)
                        }
                    }

                    val extensions = if (target.name.endsWith("X64")) {
                        listOf(
                            "avx2",
                            "avx",
                            "fma3_avx2",
                            "fma3_avx",
                            "fma3_sse4_2",
                            "sse2",
                            "sse3",
                            "sse4_1",
                            "sse4_2",
                            "ssse3",
                        )
                    } else {
                        listOf(
                            "neon64",
                        )
                    }

                    fun flagsFor(ext: String): Array<String> = when (ext) {
                        // == X86 ==
                        "avx2" -> arrayOf("-mavx2") + flagsFor("avx")
                        "avx" -> arrayOf("-mavx")
                        "fma3_avx2" -> arrayOf("-mfma") + flagsFor("avx2")
                        "fma3_avx" -> arrayOf("-mfma") + flagsFor("avx")
                        "fma3_sse4_2" -> arrayOf("-mfma") + flagsFor("sse4_2")
                        "sse2" -> arrayOf("-msse2")
                        "sse3" -> arrayOf("-msse3") + flagsFor("sse2")
                        "sse4_1" -> arrayOf("-msse4.1") + flagsFor("ssse3")
                        "sse4_2" -> arrayOf("-msse4.2") + flagsFor("sse4_1")
                        "ssse3" -> arrayOf("-mssse3") + flagsFor("sse3")

                        // == ARM ==
                        "neon64" -> arrayOf("-mfloat-abi=softfp", "-mfpu=neon")  // NEON is supposedly enabled by default?
                        else -> throw IllegalArgumentException("Unknown extension: $ext")
                    }


                    val entries = extensions.map { file ->
                        val inFile = projectDir.resolve("src/lib/arch/$file.cpp")
                        val outFile = layout.buildDirectory.file("cinterop/${target.name}/$file.o").get().asFile.also { it.parentFile.mkdirs() }.absolutePath

                        val task = tasks.register<KonanCompileTask>("compileSimd${file.capitalized()}${target.name.capitalized()}", target.konanTarget).apply {
                            configure {
                                inputs.dir(project.projectDir.resolve("src/lib"))
                                outputs.file(outFile)

                                files.from(inFile)
                                arguments.addAll(
                                    "-c", "-o", outFile,
                                    "-fPIC", "-O2",
                                    *flagsFor(file),
                                    *includes.map { include -> "-I${projectDir.resolve(include).absolutePath}" }.toTypedArray(),
                                )

                                if (target.name.startsWith("macos")) {
                                    arguments.addAll(*extraFlags)
                                }
                            }
                        }

                        outFile to task
                    }

                    val arTask = tasks.register<KonanArTask>("arSimd${target.name.capitalized()}", target.konanTarget).apply {
                        configure {
                            outputDirectory = layout.buildDirectory.dir("static/${target.name}")
                            outputFileName = "libsimd_impl.a"

                            for ((file, task) in entries) {
                                files.from(file)
                                dependsOn(task)
                            }
                        }
                    }

                    val arTaskImpl = arTask.get()
                    this@apply.compilations["main"].compileTaskProvider.configure {
                        compilerOptions.freeCompilerArgs.addAll(
                            "-include-binary", "${arTaskImpl.outputDirectory.get().asFile.absolutePath}/${arTaskImpl.outputFileName.get()}"
                        )
                    }

                    tasks.named(interopProcessingTaskName, CInteropProcess::class) {
                        dependsOn(arTask)
                    }
                }
            }
        }
    }

    sourceSets {
        commonTest {
            dependencies {
                api(kotlin("test"))
            }
        }
    }
}

tasks {
    withType<KotlinNativeCompile> {
        compilerOptions {
            if (!target.startsWith("mingw")) {
                freeCompilerArgs.add("-Xbinary=sourceInfoType=libbacktrace")
            }

            optIn = listOf(
                "kotlin.experimental.ExperimentalNativeApi",
                "kotlinx.cinterop.ExperimentalForeignApi",
            )
        }
    }

    val jvmProcessResources by existing(Copy::class) {
        val binaryName = if (isProduction) {
            "releaseShared"
        } else {
            "debugShared"
        }

        for (native in kotlin.targets.withType<KotlinNativeTarget>()) {
            into("META-INF/natives/${native.targetName}") {
                from(named(native.binaries.getByName(binaryName).linkTaskName)) {
                    exclude("**/*.h")
                }
            }
        }
    }
}

if (isProduction) {
    val isTagged = Runtime.getRuntime().exec("git tag --points-at HEAD").inputStream.reader().readText().isNotBlank()

    val releaseVersion = if (isTagged) {
        version.toString()
    } else {
        val tag = Runtime.getRuntime().exec("git rev-parse --short HEAD").inputStream.reader().readText().trim()
        "$version-$tag"
    }

    publishing {
        repositories {
            maven {
                name = "Martmists-Maven"
                url = uri("https://maven.martmists.com/${if (isTagged) "releases" else "snapshots"}")
                credentials {
                    username = if (project.hasProperty("mavenToken")) {
                        "admin"
                    } else {
                        System.getenv("MAVEN_USER")
                    } ?: error("No maven user found")
                    password = if (project.hasProperty("mavenToken")) {
                        project.ext["mavenToken"] as? String
                    } else {
                        System.getenv("MAVEN_TOKEN")
                    } ?: error("No maven token found")
                }
            }
        }

        publications {
            withType<MavenPublication> {
                version = releaseVersion
            }
        }
    }
}

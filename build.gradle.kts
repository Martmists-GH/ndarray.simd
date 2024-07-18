import com.github.tomtzook.gcmake.tasks.CmakeBuildTask
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithSimulatorTests
import org.jetbrains.kotlin.gradle.targets.native.tasks.artifact.KotlinNativeLinkArtifactTask
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

plugins {
    kotlin("multiplatform") version "2.0.0"
    id("io.github.tomtzook.gradle-cmake") version "1.2.2"
}

group = "com.martmists"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

cmake {
    targets {
        val simd by creating {
            cmakeLists = file("cmake/CMakeLists.txt")

            val linuxX64 by machines.customMachines.registering {
                toolchainFile = file("cmake/toolchains/linux-x64.cmake")
            }
            val linuxArm64 by machines.customMachines.registering {
                toolchainFile = file("cmake/toolchains/linux-arm64.cmake")
            }
            val mingwX64 by machines.customMachines.registering {
                toolchainFile = file("cmake/toolchains/mingw-x64.cmake")
            }
            val mingwArm64 by machines.customMachines.registering {
                toolchainFile = file("cmake/toolchains/mingw-arm64.cmake")
            }
            val macosX64 by machines.customMachines.registering {
                toolchainFile = file("cmake/toolchains/macos-x64.cmake")
            }
            val macosArm64 by machines.customMachines.registering {
                toolchainFile = file("cmake/toolchains/macos-arm64.cmake")
            }

            if (project.hasProperty("production")) {
                targetMachines.add(linuxX64)
                targetMachines.add(linuxArm64)
                targetMachines.add(mingwX64)
//                targetMachines.add(mingwArm64)
                targetMachines.add(macosX64)
                targetMachines.add(macosArm64)
            } else {
                targetMachines.add(linuxX64)
            }

            cmakeArgs = if (project.hasProperty("production")) {
                listOf("-DCMAKE_BUILD_TYPE=Release")
            } else {
                listOf("-DCMAKE_BUILD_TYPE=Debug")
            }
        }
    }
}

kotlin {
    jvm()

    val natives = if (project.hasProperty("production")) {
        listOf(
            linuxX64(),
            linuxArm64(),
            mingwX64(),
//            mingwArm64(),
            macosX64(),
            macosArm64(),
        )
    } else {
        when (System.getProperty("os.name")) {
            "Linux" -> listOf(linuxX64())
            "Windows" -> listOf(mingwX64())
            "Mac OS X" -> listOf(macosX64())
            else -> error("Unsupported OS")
        }
    }

    for (native in natives) {
        native.apply {
            binaries {
                sharedLib {
                    baseName = "ndarray_simd"
                }
            }

            compilerOptions {
                optIn = listOf(
                    "kotlin.experimental.ExperimentalNativeApi",
                    "kotlinx.cinterop.ExperimentalForeignApi",
                )
            }

            compilations.named("main") {
                val jni by cinterops.creating {
                    val javaHome = File(System.getProperty("java.home")!!)
                    defFile(project.projectDir.resolve("src/nativeMain/cinterops/jni.def"))
                    includeDirs(
                        javaHome.resolve("include"),
                        javaHome.resolve("include/linux"),
                        javaHome.resolve("include/darwin"),
                        javaHome.resolve("include/win32"),
                    )
                }

                val simd by cinterops.creating {
                    defFile(project.projectDir.resolve("src/nativeMain/cinterops/simd.def"))
                    includeDirs(
                        project.projectDir.resolve("src/lib"),
                    )

                    extraOpts("-libraryPath", projectDir.resolve("build/cmake/simd/${target.name}/").absolutePath)
                }
            }
        }
    }
}

tasks {
    val cmakeBuild by existing

    withType<KotlinNativeCompile> {
        dependsOn(cmakeBuild)
    }

    val jvmProcessResources by existing(Copy::class) {
        val binaryName = if (project.hasProperty("production")) {
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

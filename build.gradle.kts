import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

plugins {
    kotlin("multiplatform") version "2.0.0"
    id("io.github.tomtzook.gradle-cmake") version "1.2.2"
    publishing
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
                val osName = System.getProperty("os.name")
                val osArch = System.getProperty("os.arch")

                val hostMachine = when (osArch) {
                    "amd64", "x86_64" -> when {
                        osName.startsWith("Linux") -> linuxX64
                        osName.startsWith("Windows") -> mingwX64
                        osName.startsWith("Mac OS X") -> macosX64
                        else -> error("Unsupported OS: $osName")
                    }
                    "arm64", "aarch64" -> when {
                        osName.startsWith("Linux") -> linuxArm64
                        osName.startsWith("Windows") -> error("Unsupported OS: Windows on ARM")
                        osName.startsWith("Mac OS X") -> macosArm64
                        else -> error("Unsupported OS: $osName")
                    }
                    else -> error("Unsupported architecture: $osArch")
                }

                targetMachines.add(hostMachine)
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

if (project.hasProperty("production")) {
    val isTagged = Runtime.getRuntime().exec("git tag --points-at HEAD").inputStream.readAllBytes().decodeToString().isNotBlank()

    val releaseVersion = if (isTagged) {
        version.toString()
    } else {
        val tag = Runtime.getRuntime().exec("git rev-parse --short HEAD").inputStream.readAllBytes().decodeToString().trim()
        "$version-$tag"
    }

    publishing {
        repositories {
            maven {
                name = "Martmists Maven"
                url = uri("https://maven.martmists.com/${if (isTagged) "releases" else "snapshots"}")
                credentials {
                    username = "admin"
                    password = project.ext["mavenToken"] as? String ?: System.getenv("MAVEN_TOKEN") ?: error("No maven token found")
                }
            }
        }

        publications.withType<MavenPublication> {
            version = releaseVersion
        }
    }
}

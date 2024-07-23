import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

plugins {
    kotlin("multiplatform") version "2.0.0"
    `maven-publish`
}

group = "com.martmists.ndarray-simd"
version = "1.0.9"
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

                    if (target.name.startsWith("macos")) {
                        extraOpts("-Xsource-compiler-option", "-v")
                        extraOpts("-Xsource-compiler-option", "-I/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/usr/include/c++/v1/")
                        extraOpts("-Xsource-compiler-option", "-I/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/usr/include/")
                        extraOpts("-Xsource-compiler-option", "-I~/.konan/dependencies/apple-llvm-20200714-macos-${if (System.getProperty("os.arch") in arrayOf("amd64", "x86_64")) "x64" else "aarch64"}-essentials/lib/clang/11.1.0/include/")
                    }

                    val cppSource = projectDir.resolve("src/lib/cpp").listFiles().filter { it.extension == "cpp" }.map { it.absolutePath }
                    cppSource.forEach {
                        extraOpts("-Xcompile-source", it)
                    }

                    val includes = listOf("src/lib/public", "xsimd/include")
                    includes.forEach {
                        extraOpts("-Xsource-compiler-option", "-I${projectDir.resolve(it).absolutePath}")
                    }
                }
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

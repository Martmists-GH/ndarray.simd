@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.tasks.CInteropProcess
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    kotlin("multiplatform")
    `maven-publish`
    id("com.android.library")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

group = "com.martmists.ndarray-simd"
version = "1.6.0"
val isProduction = (findProperty("production") ?: System.getProperty("production")) != null

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = 35
    namespace = group.toString().replace('-', '.')
    ndkVersion = "21.4.7075529"

    sourceSets {
        getByName("debug").jniLibs.srcDirs(project.layout.buildDirectory.dir("generated/jni/Debug"))
        getByName("release").jniLibs.srcDirs(project.layout.buildDirectory.dir("generated/jni/Release"))
    }
}

kotlin {
    jvmToolchain(21)

    withSourcesJar()

    androidTarget {
        publishLibraryVariants("release", "debug")
    }
    jvm("desktop")

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

            androidNativeArm64(),
            androidNativeX64(),
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

    applyDefaultHierarchyTemplate()

    for (native in natives) {
        native.apply {
            binaries {
                sharedLib {
                    baseName = "ndarray_simd"

                    if (native.targetName.startsWith("android")) {
                        linkTaskProvider.configure {
                            doLast {
                                copy {
                                    from(outputFile)

                                    val typeName = if (buildType == NativeBuildType.DEBUG) "Debug" else "Release"
                                    val abiDirName = when (this@sharedLib.target.konanTarget) {
                                        KonanTarget.ANDROID_ARM32 -> "armeabi-v7a"
                                        KonanTarget.ANDROID_ARM64 -> "arm64-v8a"
                                        KonanTarget.ANDROID_X86 -> "x86"
                                        KonanTarget.ANDROID_X64 -> "x86_64"
                                        else -> "unknown"
                                    }

                                    into(project.layout.buildDirectory.dir("generated/jni/$typeName/$abiDirName"))
                                }
                            }
                        }

                        afterEvaluate {
                            tasks.getByName("preBuild").dependsOn(linkTaskProvider)
                        }
                    }
                }
            }

            compilations.named("main") {
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
                            "fma4",
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
                        "fma4" -> arrayOf("-mfma4") + flagsFor("sse4_2")
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

                if (!native.targetName.startsWith("android")) {
                    val jni by cinterops.creating {
                        val javaHome = File(System.getProperty("java.home")!!)
                        defFile(projectDir.resolve("src/desktopNativeMain/cinterops/jni.def"))

                        includeDirs(
                            javaHome.resolve("include"),
                            javaHome.resolve("include/linux"),
                            javaHome.resolve("include/darwin"),
                            javaHome.resolve("include/win32"),
                        )
                    }
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting

        val commonTest by getting {
            dependencies {
                api(kotlin("test"))
            }
        }

        val jvmMain by creating {
            dependsOn(commonMain)

            dependencies {
                // Compat: OpenCV
                compileOnly("org.openpnp:opencv:4.9.0-0")

                // Compat: Exposed+PGVector
                compileOnly("com.pgvector:pgvector:0.1.6")
                compileOnly("org.jetbrains.exposed:exposed-core:0.60.0")

                // Compat: Image Formats
                compileOnly("com.sksamuel.scrimage:scrimage-core:4.1.3")
                compileOnly("com.sksamuel.scrimage:scrimage-webp:4.1.3")

                // Compat: Langchain4J
                compileOnly("dev.langchain4j:langchain4j:0.32.0")

                // Compat: kotlinx.dataframe
                compileOnly("org.jetbrains.kotlinx:dataframe-core:0.13.1")

                // Compat: Deep Java Library
                compileOnly("ai.djl:api:0.32.0")
            }
        }

        val jvmTest by creating {
            dependsOn(commonTest)

            dependencies {
                implementation("org.openpnp:opencv:4.9.0-0")
                implementation("com.sksamuel.scrimage:scrimage-core:4.1.3")
                implementation("dev.langchain4j:langchain4j:0.32.0")
                implementation("org.jetbrains.kotlinx:dataframe-core:0.13.1")
                implementation("ai.djl:api:0.32.0")

                implementation("org.jetbrains.exposed:exposed-core:0.60.0")
                implementation("org.jetbrains.exposed:exposed-jdbc:0.60.0")
                implementation("org.xerial:sqlite-jdbc:3.44.1.0")

            }
        }

        val androidMain by getting {
            dependsOn(jvmMain)
        }

        val desktopMain by getting {
            dependsOn(jvmMain)
        }

        val nativeMain by getting

        val desktopNativeMain by creating {
            dependsOn(nativeMain)
        }

        for (native in natives) {
            if (!native.targetName.startsWith("android")) {
                named("${native.targetName}Main") {
                    dependsOn(desktopNativeMain)
                }
            }
        }

//        val androidUnitTest by getting {
//            dependsOn(jvmTest)
//        }

        val desktopTest by getting {
            dependsOn(jvmTest)
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

    afterEvaluate {
        named("testDebugUnitTest") {
            enabled = false
        }
        named("testReleaseUnitTest") {
            enabled = false
        }
    }

    val desktopProcessResources by existing(Copy::class) {
        val binaryName = if (isProduction) {
            "releaseShared"
        } else {
            "debugShared"
        }

        for (native in kotlin.targets.withType<KotlinNativeTarget>().filter { !it.name.startsWith("android") }) {
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

    fun MavenPom.configure() {
        name = "NDArray.simd"
        description = "Kotlin/Multiplatform NDArray with SIMD optimizations and low memory footprint"
        url = "https://github.com/martmists-gh/ndarray.simd"

        licenses {
            license {
                name = "3-Clause BSD NON-AI License"
                url = "https://github.com/non-ai-licenses/non-ai-licenses/blob/main/NON-AI-BSD3"
                distribution = "repo"
            }
        }

        developers {
            developer {
                id = "Martmists"
                name = "Martmists"
                url = "https://github.com/martmists-gh"
            }
        }

        scm {
            url = "https://github.com/martmists-gh/ndarray.simd"
        }
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
                        project.properties["mavenToken"] as? String
                    } else {
                        System.getenv("MAVEN_TOKEN")
                    } ?: error("No maven token found")
                }
            }
        }

        publications {
            withType<MavenPublication> {
                version = releaseVersion
                pom {
                    configure()
                }
            }
        }
    }

    mavenPublishing {
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
        coordinates(group.toString(), name, releaseVersion)
        signAllPublications()

        pom {
            configure()
        }
    }
}

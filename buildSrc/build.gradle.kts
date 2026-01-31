plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation(kotlin("gradle-plugin", "2.3.0"))
    implementation("com.vanniktech.maven.publish:com.vanniktech.maven.publish.gradle.plugin:0.31.0")
    implementation("org.jetbrains.dokka:org.jetbrains.dokka.gradle.plugin:2.0.0")
    implementation("com.android.tools.build:gradle:8.9.2")
}

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("gradle-plugin", "2.1.0"))
    implementation("com.vanniktech.maven.publish:com.vanniktech.maven.publish.gradle.plugin:0.30.0")
    implementation("org.jetbrains.dokka:org.jetbrains.dokka.gradle.plugin:2.0.0")
}

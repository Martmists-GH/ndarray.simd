# NDArray.SIMD

A Kotlin NDArray library with built-in SIMD support.

### Installation

```kotlin
repositories {
    maven("https://maven.martmists.com/releases")
}

dependencies {
    implementation("com.martmists.ndarray-simd:ndarray-simd:1.0.0")
}
```

### Motivation

I basically made this because [Viktor](https://github.com/JetBrains-Research/viktor) didn't really utilize their SIMD capabilities.

As such, most of the JVM code for NDArray is mostly the same as Viktor.

### License

The nativeMain and jvmMain sourcesets are licensed under the [3-Clause BSD NON-AI License](https://github.com/non-ai-licenses/non-ai-licenses/blob/main/NON-AI-BSD3), with @Martmists-GH as the copyright holder.

The commonMain sourceset is mostly copied/adapted from Viktor, and as such is licensed under the [original MIT license](https://github.com/JetBrains-Research/viktor/blob/master/LICENSE), with JetBrains BioLabs as the copyright holder.

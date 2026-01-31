# NDArray.SIMD

A Kotlin NDArray library with built-in SIMD support.

### Installation

```kotlin
repositories {
    mavenCentral()
    // OR, more reliably (because mavenCentral likes to not validate files sometimes):
    maven("https://maven.martmists.com/releases")
}

dependencies {
    implementation("com.martmists.ndarray-simd:ndarray-simd:1.7.6")
}
```

### Motivation

I basically made this because [Viktor](https://github.com/JetBrains-Research/viktor) didn't really utilize their SIMD capabilities.

As such, most of the JVM code for NDArray is loosely based on Viktor.

### License

As of 1.0.10, this project is licensed under the [3-Clause BSD NON-AI License](https://github.com/non-ai-licenses/non-ai-licenses/blob/main/NON-AI-BSD3).

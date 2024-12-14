cmake_minimum_required(VERSION 3.10)
project(CLionConfig)

file(GLOB_RECURSE SOURCE_FILES src/lib/*.cpp)
include_directories(
    src/lib/
    xsimd/include/
)

add_library(simd STATIC ${SOURCE_FILES})

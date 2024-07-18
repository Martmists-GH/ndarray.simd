cmake_minimum_required(VERSION 3.30.0)
project(simd)

file(GLOB_RECURSE source_files ../src/lib/cpp/*.cpp)

include_directories(../src/lib/public)

add_library(
    simd STATIC ${source_files}
)

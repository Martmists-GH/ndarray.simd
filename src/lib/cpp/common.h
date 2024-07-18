#pragma once

#include <xsimd/xsimd.hpp>
#include "lib.h"

constexpr std::size_t simd_size = xsimd::simd_type<double>::size;
#define MAKE_TRUE() xsimd::batch(1.0)
#define MAKE_FALSE() xsimd::batch(0.0)

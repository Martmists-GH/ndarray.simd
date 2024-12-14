#pragma once

#include "common.h"

MAKE_SIMD(void, _vec_floor, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::floor(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::floor(a[i]);
    }
}

MAKE_SIMD(void, _vec_ceil, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::ceil(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::ceil(a[i]);
    }
}

MAKE_SIMD(void, _vec_trunc, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::trunc(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::trunc(a[i]);
    }
}

MAKE_SIMD(void, _vec_round, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::round(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::round(a[i]);
    }
}

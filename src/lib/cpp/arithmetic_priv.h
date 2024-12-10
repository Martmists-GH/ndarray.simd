#pragma once

#include "common.h"

MAKE_SIMD(void, _vec_add_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto vb = batch(b);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va + vb;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = a[i] + b;
    }
}

MAKE_SIMD(void, _vec_add_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va + vb;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = a[i] + b[i];
    }
}

MAKE_SIMD(void, _vec_sub_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto vb = batch(b);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va - vb;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = a[i] - b;
    }
}

MAKE_SIMD(void, _vec_sub_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va - vb;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = a[i] - b[i];
    }
}

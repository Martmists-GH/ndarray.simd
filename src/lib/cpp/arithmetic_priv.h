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

MAKE_SIMD(void, _vec_mul_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto vb = batch(b);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va * vb;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = a[i] * b;
    }
}

MAKE_SIMD(void, _vec_mul_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va * vb;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = a[i] * b[i];
    }
}

MAKE_SIMD(void, _vec_div_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto vb = batch(b);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va / vb;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = a[i] / b;
    }
}

MAKE_SIMD(void, _vec_div_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va / vb;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = a[i] / b[i];
    }
}

MAKE_SIMD(void, _vec_rem_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto vb = batch(b);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::fmod(va, vb);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::fmod(a[i], b);
    }
}

MAKE_SIMD(void, _vec_rem_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = xsimd::fmod(va, vb);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::fmod(a[i], b[i]);
    }
}

MAKE_SIMD(void, _vec_negate, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = -va;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = -a[i];
    }
}

MAKE_SIMD(void, _vec_abs, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::abs(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::abs(a[i]);
    }
}

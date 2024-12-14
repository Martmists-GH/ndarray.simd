#pragma once

#include "common.h"

MAKE_SIMD(void, _vec_sqrt, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::sqrt(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::sqrt(a[i]);
    }
}

MAKE_SIMD(void, _vec_pow, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto vb = batch(b);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::pow(va, vb);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::pow(a[i], b);
    }
}

MAKE_SIMD(void, _vec_ipow, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto vb = batch(b);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::pow(vb, va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::pow(b, a[i]);
    }
}

MAKE_SIMD(void, _vec_log, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::log(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::log(a[i]);
    }
}

MAKE_SIMD(void, _vec_logbase, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto lb = std::log(b);
    auto vb = batch(lb);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::log(va) / vb;
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::log(a[i]) / lb;
    }
}

MAKE_SIMD(void, _vec_exp, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::exp(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::exp(a[i]);
    }
}

MAKE_SIMD(void, _vec_expm1, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::expm1(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::expm1(a[i]);
    }
}

MAKE_SIMD(void, _vec_log1p, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::log1p(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::log1p(a[i]);
    }
}

MAKE_SIMD(void, _vec_log2, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::log2(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::log2(a[i]);
    }
}

MAKE_SIMD(void, _vec_log10, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::log10(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::log10(a[i]);
    }
}

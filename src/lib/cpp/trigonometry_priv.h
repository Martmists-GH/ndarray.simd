#pragma once

#include "common.h"

MAKE_SIMD(void, _vec_sin, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::sin(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::sin(a[i]);
    }
}

MAKE_SIMD(void, _vec_cos, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::cos(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::cos(a[i]);
    }
}

MAKE_SIMD(void, _vec_tan, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::tan(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::tan(a[i]);
    }
}

MAKE_SIMD(void, _vec_asin, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::asin(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::asin(a[i]);
    }
}

MAKE_SIMD(void, _vec_acos, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::acos(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::acos(a[i]);
    }
}

MAKE_SIMD(void, _vec_atan, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::atan(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::atan(a[i]);
    }
}

MAKE_SIMD(void, _vec_atan2, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = xsimd::atan2(va, vb);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::atan2(a[i], b[i]);
    }
}

MAKE_SIMD(void, _vec_sinh, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::sinh(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::sinh(a[i]);
    }
}

MAKE_SIMD(void, _vec_cosh, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::cosh(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::cosh(a[i]);
    }
}

MAKE_SIMD(void, _vec_tanh, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::tanh(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::tanh(a[i]);
    }
}

MAKE_SIMD(void, _vec_asinh, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::asinh(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::asinh(a[i]);
    }
}

MAKE_SIMD(void, _vec_acosh, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::acosh(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::acosh(a[i]);
    }
}

MAKE_SIMD(void, _vec_atanh, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::atanh(va);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::atanh(a[i]);
    }
}

MAKE_SIMD(void, _vec_hypot, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = xsimd::hypot(va, vb);
        xsimd::store_unaligned(&a[i], res);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::hypot(a[i], b[i]);
    }
}

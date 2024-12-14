#pragma once

#include "common.h"

MAKE_SIMD(void, _vec_and_scalar, double* a, int b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = to_int(va) & b;
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) & b);
    }
}

MAKE_SIMD(void, _vec_and_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
       auto va = batch::load_unaligned(&a[i]);
       auto vb = batch::load_unaligned(&b[i]);
       auto res = to_int(va) & to_int(vb);
       xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) & static_cast<int>(b[i]));
    }
}

MAKE_SIMD(void, _vec_or_scalar, double* a, int b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = to_int(va) | b;
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) | b);
    }
}

MAKE_SIMD(void, _vec_or_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = to_int(va) | to_int(vb);
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) | static_cast<int>(b[i]));
    }
}

MAKE_SIMD(void, _vec_xor_scalar, double* a, int b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = to_int(va) ^ b;
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) ^ b);
    }
}

MAKE_SIMD(void, _vec_xor_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = to_int(va) ^ to_int(vb);
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) ^ static_cast<int>(b[i]));
    }
}

MAKE_SIMD(void, _vec_not, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = ~to_int(va);
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(~static_cast<int>(a[i]));
    }
}

MAKE_SIMD(void, _vec_lshift_scalar, double* a, int b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = to_int(va) << b;
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) << b);
    }
}

MAKE_SIMD(void, _vec_lshift_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = to_int(va) << to_int(vb);
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) << static_cast<int>(b[i]));
    }
}

MAKE_SIMD(void, _vec_rshift_scalar, double* a, int b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = to_int(va) >> b;
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) >> b);
    }
}

MAKE_SIMD(void, _vec_rshift_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = to_int(va) >> to_int(vb);
        xsimd::store_unaligned(&a[i], to_float(res));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = static_cast<double>(static_cast<int>(a[i]) >> static_cast<int>(b[i]));
    }
}


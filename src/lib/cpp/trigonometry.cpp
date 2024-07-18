#include "common.h"

extern "C" {
    void vec_sin(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::sin(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::sin(a[i]);
        }
    }

    void vec_cos(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::cos(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::cos(a[i]);
        }
    }

    void vec_tan(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::tan(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::tan(a[i]);
        }
    }

    void vec_asin(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::asin(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::asin(a[i]);
        }
    }

    void vec_acos(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::acos(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::acos(a[i]);
        }
    }

    void vec_atan(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::atan(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::atan(a[i]);
        }
    }

    void vec_atan2(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = xsimd::atan2(va, vb);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::atan2(a[i], b[i]);
        }
    }

    void vec_sinh(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::sinh(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::sinh(a[i]);
        }
    }

    void vec_cosh(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::cosh(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::cosh(a[i]);
        }
    }

    void vec_tanh(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::tanh(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::tanh(a[i]);
        }
    }

    void vec_asinh(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::asinh(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::asinh(a[i]);
        }
    }

    void vec_acosh(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::acosh(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::acosh(a[i]);
        }
    }

    void vec_atanh(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::atanh(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::atanh(a[i]);
        }
    }

    void vec_hypot(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = xsimd::hypot(va, vb);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::hypot(a[i], b[i]);
        }
    }
}

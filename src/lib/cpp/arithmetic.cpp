#include "arithmetic_priv.h"

extern "C" {
    void vec_add_scalar(double* a, double b, int n) {
        _vec_add_scalar_dispatcher(a, b, n);
    }

    void vec_add_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va + vb;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] + b[i];
        }
    }

    void vec_sub_scalar(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = va - b;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] - b;
        }
    }

    void vec_sub_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va - vb;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] - b[i];
        }
    }

    void vec_mul_scalar(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = va * b;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] * b;
        }
    }

    void vec_mul_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va * vb;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] * b[i];
        }
    }

    void vec_div_scalar(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = va / b;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] / b;
        }
    }

    void vec_div_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va / vb;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] / b[i];
        }
    }

    void vec_negate(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = -va;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = -a[i];
        }
    }

    void vec_abs(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::abs(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::abs(a[i]);
        }
    }
}

#include "common.h"

extern "C" {
    void vec_sqrt(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::sqrt(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::sqrt(a[i]);
        }
    }

    void vec_pow(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto vb = xsimd::batch(b);

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::pow(va, vb);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::pow(a[i], b);
        }
    }

    void vec_ipow(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto vb = xsimd::batch(b);

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::pow(vb, va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::pow(a[i], b);
        }
    }

    void vec_log(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::log(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::log(a[i]);
        }
    }

    void vec_logbase(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto lb = std::log(b);
        auto vb = xsimd::batch(lb);

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::log(va) / vb;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::log(a[i]) / lb;
        }
    }

    void vec_exp(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::exp(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::exp(a[i]);
        }
    }

    void vec_expm1(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::expm1(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::expm1(a[i]);
        }
    }

    void vec_log1p(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::log1p(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::log1p(a[i]);
        }
    }

    void vec_log2(double* a, int n) {
        std::size_t size = n - n % simd_size;
        auto vb = xsimd::batch(std::log(2.0));

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::log(va) / vb;
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::log2(a[i]);
        }
    }

    void vec_log10(double* a, int n) {
        std::size_t size = n - n % simd_size;
        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::log10(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::log10(a[i]);
        }
    }
}

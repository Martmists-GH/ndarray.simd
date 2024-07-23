#include "common.h"

extern "C" {
    void vec_floor(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::floor(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::floor(a[i]);
        }
    }

    void vec_ceil(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::ceil(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::ceil(a[i]);
        }
    }

    void vec_trunc(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::trunc(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::trunc(a[i]);
        }
    }

    void vec_round(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::round(va);
            xsimd::store_unaligned(&a[i], res);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::round(a[i]);
        }
    }
}

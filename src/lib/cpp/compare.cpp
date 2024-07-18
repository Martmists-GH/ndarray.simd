#include "common.h"

extern "C" {
    void vec_eq_vec(double* arr, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va == vb;
            xsimd::store_unaligned(&arr[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = arr[i] == b[i] ? 1.0 : 0.0;
        }
    }

    void vec_eq_scalar(double* arr, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = va == b;
            xsimd::store_unaligned(&arr[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = arr[i] == b ? 1.0 : 0.0;
        }
    }

    void vec_neq_vec(double* arr, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va != vb;
            xsimd::store_unaligned(&arr[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = arr[i] != b[i] ? 1.0 : 0.0;
        }
    }

    void vec_neq_scalar(double* arr, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = va != b;
            xsimd::store_unaligned(&arr[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = arr[i] != b ? 1.0 : 0.0;
        }
    }

    void vec_lt_vec(double* arr, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va < vb;
            xsimd::store_unaligned(&arr[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = arr[i] < b[i] ? 1.0 : 0.0;
        }
    }

    void vec_lt_scalar(double* arr, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = va < b;
            xsimd::store_unaligned(&arr[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = arr[i] < b ? 1.0 : 0.0;
        }
    }

    void vec_gt_vec(double* arr, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va > vb;
            xsimd::store_unaligned(&arr[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = arr[i] > b[i] ? 1.0 : 0.0;
        }
    }

    void vec_gt_scalar(double* arr, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = va > b;
            xsimd::store_unaligned(&arr[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = arr[i] > b ? 1.0 : 0.0;
        }
    }
}

#include "common.h"

extern "C" {
    void vec_eq_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va == vb;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] == b[i] ? 1.0 : 0.0;
        }
    }

    void vec_eq_scalar(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = va == b;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] == b ? 1.0 : 0.0;
        }
    }

    void vec_neq_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va != vb;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] != b[i] ? 1.0 : 0.0;
        }
    }

    void vec_neq_scalar(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = va != b;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] != b ? 1.0 : 0.0;
        }
    }

    void vec_lt_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va < vb;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] < b[i] ? 1.0 : 0.0;
        }
    }

    void vec_lt_scalar(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = va < b;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] < b ? 1.0 : 0.0;
        }
    }

    void vec_gt_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va > vb;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] > b[i] ? 1.0 : 0.0;
        }
    }

    void vec_gt_scalar(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = va > b;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] > b ? 1.0 : 0.0;
        }
    }

    void vec_lte_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va <= vb;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] <= b[i] ? 1.0 : 0.0;
        }
    }

    void vec_lte_scalar(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = va <= b;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] <= b ? 1.0 : 0.0;
        }
    }

    void vec_gte_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = va >= vb;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] >= b[i] ? 1.0 : 0.0;
        }
    }

    void vec_gte_scalar(double* a, double b, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = va >= b;
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = a[i] >= b ? 1.0 : 0.0;
        }
    }

    void vec_isnan(double* a, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::isnan(va);
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::isnan(a[i]) ? 1.0 : 0.0;
        }
    }

    void vec_isinf(double* a, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::isinf(va);
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::isinf(a[i]) ? 1.0 : 0.0;
        }
    }

    void vec_isfinite(double* a, int n) {
        std::size_t size = n - n % simd_size;
        auto TRUE = MAKE_TRUE();
        auto FALSE = MAKE_FALSE();

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = xsimd::isfinite(va);
            xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::isfinite(a[i]) ? 1.0 : 0.0;
        }
    }
}

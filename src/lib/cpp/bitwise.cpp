#include "common.h"

extern "C" {
    void vec_and_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) & to_int(vb);
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) & static_cast<int>(b[i]));
        }
    }

    void vec_and_scalar(double* a, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = to_int(va) & b;
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) & b);
        }
    }

    void vec_or_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) | to_int(vb);
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) | static_cast<int>(b[i]));
        }
    }

    void vec_or_scalar(double* a, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = to_int(va) | b;
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) | b);
        }
    }

    void vec_xor_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) ^ to_int(vb);
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) ^ static_cast<int>(b[i]));
        }
    }

    void vec_xor_scalar(double* a, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = to_int(va) ^ b;
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) ^ b);
        }
    }

    void vec_not(double* a, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = ~to_int(va);
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(~static_cast<int>(a[i]));
        }
    }

    void vec_lshift_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) << to_int(vb);
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) << static_cast<int>(b[i]));
        }
    }

    void vec_lshift_scalar(double* a, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = to_int(va) << b;
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) << b);
        }
    }

    void vec_rshift_vec(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) >> to_int(vb);
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) >> static_cast<int>(b[i]));
        }
    }

    void vec_rshift_scalar(double* a, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto res = to_int(va) >> b;
            xsimd::store_unaligned(&a[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = static_cast<double>(static_cast<int>(a[i]) >> b);
        }
    }
}

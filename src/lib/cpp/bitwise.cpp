#include "common.h"

extern "C" {
    void vec_and_vec(double* arr, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) & to_int(vb);
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) & static_cast<int>(b[i]));
        }
    }

    void vec_and_scalar(double* arr, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = to_int(va) & b;
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) & b);
        }
    }

    void vec_or_vec(double* arr, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) | to_int(vb);
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) | static_cast<int>(b[i]));
        }
    }

    void vec_or_scalar(double* arr, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = to_int(va) | b;
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) | b);
        }
    }

    void vec_xor_vec(double* arr, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) ^ to_int(vb);
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) ^ static_cast<int>(b[i]));
        }
    }

    void vec_xor_scalar(double* arr, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = to_int(va) ^ b;
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) ^ b);
        }
    }

    void vec_not(double* arr, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = ~to_int(va);
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(~static_cast<int>(arr[i]));
        }
    }

    void vec_lshift_vec(double* arr, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) << to_int(vb);
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) << static_cast<int>(b[i]));
        }
    }

    void vec_lshift_scalar(double* arr, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = to_int(va) << b;
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) << b);
        }
    }

    void vec_rshift_vec(double* arr, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            auto res = to_int(va) >> to_int(vb);
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) >> static_cast<int>(b[i]));
        }
    }

    void vec_rshift_scalar(double* arr, int b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&arr[i]);
            auto res = to_int(va) >> b;
            xsimd::store_unaligned(&arr[i], to_float(res));
        }

        for (std::size_t i = size; i < n; ++i) {
            arr[i] = static_cast<double>(static_cast<int>(arr[i]) >> b);
        }
    }
}

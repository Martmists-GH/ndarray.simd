#include "common.h"

extern "C" {
    double vec_dot(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;
        auto res = xsimd::batch(0.0);

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            auto vb = xsimd::load_unaligned(&b[i]);
            res += va * vb;
        }

        auto result = xsimd::reduce_add(res);

        for (std::size_t i = size; i < n; ++i) {
            result += a[i] * b[i];
        }

        return result;
    }

    void vec_matmul(double* a, double* b, double* c, int n, int m, int p) {
        auto size = m - (m % simd_size);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < p; ++j) {
                auto sum = xsimd::batch(0.0);
                std::size_t k = 0;
                for (; k < size; k += simd_size) {
                    // Need to do unaligned load here
                    auto va = xsimd::load_unaligned(&a[i * m + k]);
                    auto vb = xsimd::load_unaligned(&b[k * p + j]);
                    sum += va * vb;
                }
                double scalar_sum = xsimd::reduce_add(sum);
                for (; k < m; ++k) {
                    scalar_sum += a[i * m + k] * b[k * p + j];
                }
                c[i * p + j] = scalar_sum;
            }
        }
    }
}

#pragma once

#include "common.h"

MAKE_SIMD(double, _vec_dot, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    auto res = batch(0.0);

    for (std::size_t i=0; i<size; i+=batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        res += va * vb;
    }

    auto result = xsimd::reduce_add(res);

    for (std::size_t i = size; i < n; ++i) {
        result += a[i] * b[i];
    }

    return result;
}

MAKE_SIMD(void, _vec_matmul, double* a, double* b, double* c, int n, int m, int p) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = m - m % batch::size;

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < p; ++j) {
            auto sum = batch(0.0);
            std::size_t k = 0;
            for (; k < size; k += simd_size) {
                // Need to do unaligned load here
                auto va = batch::load_unaligned(&a[i * m + k]);
                auto vb = batch::load_unaligned(&b[k * p + j]);
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

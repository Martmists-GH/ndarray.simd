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

MAKE_SIMD(void, _vec_matmul, double* a, double* b, double* c, int m, int n, int p) {
    using batch = xsimd::batch<double, Arch>;

    // a = mxn
    // b = nxp
    // c = mxp
    // n = common size, leading dim for both (i.e. a[0] is [0, 0], a[1] is [0, 1])
    // b is transposed to pxn

    for (int i = 0; i < m; ++i) {
        for (int j = 0; j < p; ++j) {
            auto sum = batch(0.0);
            int k = 0;

            for (; k + batch::size <= n; k += batch::size) {
                auto va = batch::load_unaligned(&a[i * n + k]);
                auto vb = batch::load_unaligned(&b[j * n + k]);
                sum += va * vb;
            }

            double partial_sum = xsimd::reduce_add(sum);

            for (; k < n; ++k) {
                partial_sum += a[i * n + k] * b[j * n + k];
            }

            c[i * p + j] = partial_sum;
        }
    }
}

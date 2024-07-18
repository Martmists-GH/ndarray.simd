#include "common.h"

extern "C" {
    void vec_copy(double* a, double* b, int n) {
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto vb = xsimd::load_unaligned(&b[i]);
            xsimd::store_unaligned(&a[i], vb);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = b[i];
        }
    }

    int get_simd_size() {
        return simd_size;
    }
}

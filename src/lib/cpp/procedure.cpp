#include "common.h"

extern "C" {
    double vec_sum(double* a, int n) {
        auto sum = xsimd::batch(0.0);
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            sum += va;
        }

        double result = xsimd::reduce_add(sum);

        for (std::size_t i = size; i < n; ++i) {
            result += a[i];
        }

        return result;
    }

    double vec_min(double* a, int n) {
        auto min = xsimd::batch(std::numeric_limits<double>::max());
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            min = xsimd::min(min, va);
        }

        double result = xsimd::reduce_min(min);

        for (std::size_t i = size; i < n; ++i) {
            result = std::min(result, a[i]);
        }

        return result;
    }

    double vec_max(double* a, int n) {
        auto max = xsimd::batch(std::numeric_limits<double>::lowest());
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            max = xsimd::max(max, va);
        }

        double result = xsimd::reduce_max(max);

        for (std::size_t i = size; i < n; ++i) {
            result = std::max(result, a[i]);
        }

        return result;
    }

    double vec_prod(double* a, int n) {
        auto prod = xsimd::batch(1.0);
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            prod *= va;
        }

        double result = 1.0;

        // FIXME: Figure out how to do this with xsimd::reduce
        for (std::size_t i = 0; i < simd_size; ++i) {
            result *= prod.get(i);
        }

        for (std::size_t i = size; i < n; ++i) {
            result *= a[i];
        }

        return result;
    }

    double vec_mean(double* a, int n) {
        return vec_sum(a, n) / n;
    }

    double vec_var(double* a, int n) {
        double mean = vec_mean(a, n);
        auto sum = xsimd::batch(0.0);
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            sum += (va - mean) * (va - mean);
        }

        double result = xsimd::reduce_add(sum);

        for (std::size_t i = size; i < n; ++i) {
            result += (a[i] - mean) * (a[i] - mean);
        }

        return result / n;
    }

    double vec_std(double* a, int n) {
        return std::sqrt(vec_var(a, n));
    }

    void vec_coerce(double* a, int n, double min, double max) {
        auto min_batch = xsimd::batch(min);
        auto max_batch = xsimd::batch(max);
        std::size_t size = n - n % simd_size;

        for (std::size_t i = 0; i < size; i += simd_size) {
            auto va = xsimd::load_unaligned(&a[i]);
            va = xsimd::min(va, max_batch);
            va = xsimd::max(va, min_batch);
            xsimd::store_unaligned(&a[i], va);
        }

        for (std::size_t i = size; i < n; ++i) {
            a[i] = std::min(std::max(a[i], min), max);
        }
    }
}

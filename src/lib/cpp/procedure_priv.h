#pragma once

#include "common.h"

MAKE_SIMD(double, _vec_sum, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    auto sum = batch(0.0);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        sum += va;
    }

    double result = xsimd::reduce_add(sum);

    for (std::size_t i = size; i < n; ++i) {
        result += a[i];
    }

    return result;
}

MAKE_SIMD(double, _vec_min, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    auto min_value = batch(std::numeric_limits<double>::max());

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        min_value = xsimd::min(min_value, va);
    }

    double result = xsimd::reduce_min(min_value);

    for (std::size_t i = size; i < n; ++i) {
        result = std::min(result, a[i]);
    }

    return result;
}

MAKE_SIMD(double, _vec_max, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    auto max_value = batch(std::numeric_limits<double>::lowest());

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        max_value = xsimd::max(max_value, va);
    }

    double result = xsimd::reduce_max(max_value);

    for (std::size_t i = size; i < n; ++i) {
        result = std::max(result, a[i]);
    }

    return result;
}

MAKE_SIMD(double, _vec_prod, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    auto prod_value = batch(1.0);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        prod_value *= va;
    }

    double result = 1.0;

    for (std::size_t i = 0; i < batch::size; ++i) {
        result *= prod_value.get(i);
    }

    for (std::size_t i = n - n % batch::size; i < n; ++i) {
        result *= a[i];
    }

    return result;
}

MAKE_SIMD(double, _vec_var, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    double mean = _vec_sum().operator()(Arch{}, a, n) / n;
    auto sum = batch(0.0);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto diff = va - mean;
        sum += diff * diff;
    }

    double result = xsimd::reduce_add(sum);

    for (std::size_t i = n - n % batch::size; i < n; ++i) {
        auto tmp = a[i] - mean;
        result += tmp * tmp;
    }

    return result / n;
}

MAKE_SIMD(void, _vec_coerce, double* a, int n, double min, double max) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto min_batch = batch(min);
    auto max_batch = batch(max);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        va = xsimd::min(va, max_batch);
        va = xsimd::max(va, min_batch);
        xsimd::store_unaligned(&a[i], va);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::min(max, std::max(min, a[i]));
    }
}

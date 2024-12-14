#pragma once

#include "common.h"

MAKE_SIMD(void, _vec_eq_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va == b;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] == b) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_eq_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va == vb;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] == b[i]) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_neq_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va != b;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] != b) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_neq_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va != vb;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] != b[i]) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_lt_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va < b;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] < b) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_lt_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va < vb;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] < b[i]) ? 1.0 : 0.0;
    }
}


MAKE_SIMD(void, _vec_gt_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va > b;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] > b) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_gt_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va > vb;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] > b[i]) ? 1.0 : 0.0;
    }
}


MAKE_SIMD(void, _vec_lte_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va <= b;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] <= b) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_lte_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va <= vb;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] <= b[i]) ? 1.0 : 0.0;
    }
}


MAKE_SIMD(void, _vec_gte_scalar, double* a, double b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = va >= b;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] >= b) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_gte_vec, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = va >= vb;
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = (a[i] >= b[i]) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_isnan, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::isnan(va);
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::isnan(a[i]) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_isinf, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::isinf(va);
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::isinf(a[i]) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_isfinite, double* a, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = xsimd::isfinite(va);
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = std::isinf(a[i]) ? 1.0 : 0.0;
    }
}

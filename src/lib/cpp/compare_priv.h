#pragma once

#include "common.h"

MAKE_SIMD(void, _vec_eq_scalar, double* a, double b, int n, double rtol, double atol, bool allow_nan) {
    using batch = xsimd::batch<double, Arch>;
    using bool_batch = xsimd::batch_bool<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    auto delta = (atol + rtol * std::abs(b));
    auto nan_allowed = bool_batch(allow_nan && std::isnan(b));

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = (xsimd::abs(va - b) <= delta) || (nan_allowed && xsimd::isnan(va));
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = ((abs(a[i] - b) <= delta) || (allow_nan && std::isnan(a[i]))) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_eq_vec, double* a, double* b, int n, double rtol, double atol, bool allow_nan) {
    using batch = xsimd::batch<double, Arch>;
    using bool_batch = xsimd::batch_bool<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    auto nan_allowed = bool_batch(allow_nan);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = (xsimd::abs(va - vb) <= (atol + rtol * xsimd::abs(vb))) || (nan_allowed && xsimd::isnan(va) && xsimd::isnan(vb));
        xsimd::store_unaligned(&a[i], select(res, TRUE, FALSE));
    }

    for (std::size_t i = size; i < n; ++i) {
        auto as = a[i];
        auto bs = b[i];
        a[i] = ((abs(as - bs) <= (atol + rtol * std::abs(bs))) || (allow_nan && std::isnan(as) && std::isnan(bs))) ? 1.0 : 0.0;
    }
}

MAKE_SIMD(void, _vec_neq_scalar, double* a, double b, int n, double rtol, double atol, bool allow_nan) {
    using batch = xsimd::batch<double, Arch>;
    using bool_batch = xsimd::batch_bool<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    auto delta = (atol + rtol * std::abs(b));
    auto nan_allowed = bool_batch(allow_nan && std::isnan(b));

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto res = (xsimd::abs(va - b) <= delta) || (nan_allowed && xsimd::isnan(va));
        xsimd::store_unaligned(&a[i], select(res, FALSE, TRUE));
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = ((abs(a[i] - b) <= delta) || (allow_nan && std::isnan(a[i]))) ? 0.0 : 1.0;
    }
}

MAKE_SIMD(void, _vec_neq_vec, double* a, double* b, int n, double rtol, double atol, bool allow_nan) {
    using batch = xsimd::batch<double, Arch>;
    using bool_batch = xsimd::batch_bool<double, Arch>;

    std::size_t size = n - n % batch::size;
    auto TRUE = TRUE();
    auto FALSE = FALSE();

    auto nan_allowed = bool_batch(allow_nan);

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto va = batch::load_unaligned(&a[i]);
        auto vb = batch::load_unaligned(&b[i]);
        auto res = (xsimd::abs(va - vb) <= (atol + rtol * xsimd::abs(vb))) || (nan_allowed && xsimd::isnan(va) && xsimd::isnan(vb));
        xsimd::store_unaligned(&a[i], select(res, FALSE, TRUE));
    }

    for (std::size_t i = size; i < n; ++i) {
        auto as = a[i];
        auto bs = b[i];
        a[i] = ((abs(as - bs) <= (atol + rtol * std::abs(bs))) || (allow_nan && std::isnan(as) && std::isnan(bs))) ? 0.0 : 1.0;
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

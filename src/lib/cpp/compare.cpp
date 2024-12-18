#include "compare_priv.h"

extern "C" {
    void vec_eq_scalar(double* a, double b, int n, double rtol, double atol, bool allow_nan) {
        _vec_eq_scalar_dispatcher(a, b, n, rtol, atol, allow_nan);
    }

    void vec_eq_vec(double* a, double* b, int n, double rtol, double atol, bool allow_nan) {
        _vec_eq_vec_dispatcher(a, b, n, rtol, atol, allow_nan);
    }

    void vec_neq_scalar(double* a, double b, int n, double rtol, double atol, bool allow_nan) {
        _vec_neq_scalar_dispatcher(a, b, n, rtol, atol, allow_nan);
    }

    void vec_neq_vec(double* a, double* b, int n, double rtol, double atol, bool allow_nan) {
        _vec_neq_vec_dispatcher(a, b, n, rtol, atol, allow_nan);
    }

    void vec_lt_scalar(double* a, double b, int n) {
        _vec_lt_scalar_dispatcher(a, b, n);
    }

    void vec_lt_vec(double* a, double* b, int n) {
        _vec_lt_vec_dispatcher(a, b, n);
    }

    void vec_lte_scalar(double* a, double b, int n) {
        _vec_lte_scalar_dispatcher(a, b, n);
    }

    void vec_lte_vec(double* a, double* b, int n) {
        _vec_lte_vec_dispatcher(a, b, n);
    }

    void vec_gt_scalar(double* a, double b, int n) {
        _vec_gt_scalar_dispatcher(a, b, n);
    }

    void vec_gt_vec(double* a, double* b, int n) {
        _vec_gt_vec_dispatcher(a, b, n);
    }

    void vec_gte_scalar(double* a, double b, int n) {
        _vec_gte_scalar_dispatcher(a, b, n);
    }

    void vec_gte_vec(double* a, double* b, int n) {
        _vec_gte_vec_dispatcher(a, b, n);
    }

    void vec_isnan(double *a, int n) {
        _vec_isnan_dispatcher(a, n);
    }

    void vec_isinf(double *a, int n) {
        _vec_isinf_dispatcher(a, n);
    }

    void vec_isfinite(double* a, int n) {
        _vec_isfinite_dispatcher(a, n);
    }
}

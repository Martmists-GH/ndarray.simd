#include "math_priv.h"

extern "C" {
    void vec_sqrt(double* a, int n) {
        _vec_sqrt_dispatcher(a, n);
    }

    void vec_pow(double* a, double b, int n) {
        _vec_pow_dispatcher(a, b, n);
    }

    void vec_ipow(double* a, double b, int n) {
        _vec_ipow_dispatcher(a, b, n);
    }

    void vec_log(double* a, int n) {
        _vec_log_dispatcher(a, n);
    }

    void vec_logbase(double* a, double b, int n) {
        _vec_logbase_dispatcher(a, b, n);
    }

    void vec_exp(double* a, int n) {
        _vec_exp_dispatcher(a, n);
    }

    void vec_expm1(double* a, int n) {
        _vec_expm1_dispatcher(a, n);
    }

    void vec_log1p(double* a, int n) {
        _vec_log1p_dispatcher(a, n);
    }

    void vec_log2(double* a, int n) {
        _vec_log2_dispatcher(a, n);
    }

    void vec_log10(double* a, int n) {
        _vec_log10_dispatcher(a, n);
    }
}

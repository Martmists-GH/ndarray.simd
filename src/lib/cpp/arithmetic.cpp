#include "arithmetic_priv.h"

extern "C" {
    void vec_add_scalar(double* a, double b, int n) {
        _vec_add_scalar_dispatcher(a, b, n);
    }

    void vec_add_vec(double* a, double* b, int n) {
        _vec_add_vec_dispatcher(a, b, n);
    }

    void vec_sub_scalar(double* a, double b, int n) {
        _vec_sub_scalar_dispatcher(a, b, n);
    }

    void vec_sub_vec(double* a, double* b, int n) {
        _vec_sub_vec_dispatcher(a, b, n);
    }

    void vec_mul_scalar(double* a, double b, int n) {
        _vec_mul_scalar_dispatcher(a, b, n);
    }

    void vec_mul_vec(double* a, double* b, int n) {
        _vec_mul_vec_dispatcher(a, b, n);
    }

    void vec_div_scalar(double* a, double b, int n) {
        _vec_div_scalar_dispatcher(a, b, n);
    }

    void vec_div_vec(double* a, double* b, int n) {
        _vec_div_vec_dispatcher(a, b, n);
    }

    void vec_negate(double* a, int n) {
        _vec_negate_dispatcher(a, n);
    }

    void vec_abs(double* a, int n) {
        _vec_abs_dispatcher(a, n);
    }
}

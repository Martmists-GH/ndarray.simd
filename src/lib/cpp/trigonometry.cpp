#include "trigonometry_priv.h"

extern "C" {
    void vec_sin(double* a, int n) {
        _vec_sin_dispatcher(a, n);
    }

    void vec_cos(double* a, int n) {
        _vec_cos_dispatcher(a, n);
    }

    void vec_tan(double* a, int n) {
        _vec_tan_dispatcher(a, n);
    }

    void vec_asin(double* a, int n) {
        _vec_asin_dispatcher(a, n);
    }

    void vec_acos(double* a, int n) {
        _vec_acos_dispatcher(a, n);
    }

    void vec_atan(double* a, int n) {
        _vec_atan_dispatcher(a, n);
    }

    void vec_atan2(double* a, double* b, int n) {
        _vec_atan2_dispatcher(a, b, n);
    }

    void vec_sinh(double* a, int n) {
        _vec_sinh_dispatcher(a, n);
    }

    void vec_cosh(double* a, int n) {
        _vec_cosh_dispatcher(a, n);
    }

    void vec_tanh(double* a, int n) {
        _vec_tanh_dispatcher(a, n);
    }

    void vec_asinh(double* a, int n) {
        _vec_asinh_dispatcher(a, n);
    }

    void vec_acosh(double* a, int n) {
        _vec_acosh_dispatcher(a, n);
    }

    void vec_atanh(double* a, int n) {
        _vec_atanh_dispatcher(a, n);
    }

    void vec_hypot(double* a, double* b, int n) {
        _vec_hypot_dispatcher(a, b, n);
    }
}

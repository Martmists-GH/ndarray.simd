#include "bitwise_priv.h"

extern "C" {
    void vec_and_scalar(double* a, int b, int n) {
        _vec_and_scalar_dispatcher(a, b, n);
    }

    void vec_and_vec(double* a, double* b, int n) {
        _vec_and_vec_dispatcher(a, b, n);
    }

    void vec_or_scalar(double* a, int b, int n) {
        _vec_or_scalar_dispatcher(a, b, n);
    }

    void vec_or_vec(double* a, double* b, int n) {
        _vec_or_vec_dispatcher(a, b, n);
    }

    void vec_xor_scalar(double* a, int b, int n) {
        _vec_xor_scalar_dispatcher(a, b, n);
    }

    void vec_xor_vec(double* a, double* b, int n) {
        _vec_xor_vec_dispatcher(a, b, n);
    }

    void vec_not(double* a, int n) {
        _vec_not_dispatcher(a, n);
    }

    void vec_lshift_scalar(double* a, int b, int n) {
        _vec_lshift_scalar_dispatcher(a, b, n);
    }

    void vec_lshift_vec(double* a, double* b, int n) {
        _vec_lshift_vec_dispatcher(a, b, n);
    }

    void vec_rshift_scalar(double* a, int b, int n) {
        _vec_rshift_scalar_dispatcher(a, b, n);
    }

    void vec_rshift_vec(double* a, double* b, int n) {
        _vec_rshift_vec_dispatcher(a, b, n);
    }
}

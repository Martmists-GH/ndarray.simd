#include "rounding_priv.h"

extern "C" {
    void vec_floor(double* a, int n) {
       _vec_floor_dispatcher(a, n);
    }

    void vec_ceil(double* a, int n) {
        _vec_ceil_dispatcher(a, n);
    }

    void vec_trunc(double* a, int n) {
        _vec_trunc_dispatcher(a, n);
    }

    void vec_round(double* a, int n) {
        _vec_round_dispatcher(a, n);
    }
}

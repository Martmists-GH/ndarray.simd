#include "misc_priv.h"

extern "C" {
    void vec_copy(double* a, double* b, int n) {
        _vec_copy_dispatcher(a, b, n);
    }

    int get_simd_size() {
        return _get_simd_size_dispatcher();
    }
}

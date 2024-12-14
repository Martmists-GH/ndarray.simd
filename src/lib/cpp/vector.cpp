#include "vector_priv.h"

extern "C" {
    double vec_dot(double* a, double* b, int n) {
        return _vec_dot_dispatcher(a, b, n);
    }

    void vec_matmul(double* a, double* b, double* c, int n, int m, int p) {
        _vec_matmul_dispatcher(a, b, c, n, m, p);
    }
}

#include "procedure_priv.h"

extern "C" {
    double vec_sum(double* a, int n) {
        return _vec_sum_dispatcher(a, n);
    }

    double vec_min(double* a, int n) {
        return _vec_min_dispatcher(a, n);
    }

    double vec_max(double* a, int n) {
        return _vec_max_dispatcher(a, n);
    }

    double vec_prod(double* a, int n) {
        return _vec_prod_dispatcher(a, n);
    }

    double vec_mean(double* a, int n) {
        return vec_sum(a, n) / n;
    }

    double vec_var(double* a, int n) {
        return _vec_var_dispatcher(a, n);
    }

    double vec_std(double* a, int n) {
        return std::sqrt(vec_var(a, n));
    }

    void vec_coerce(double* a, int n, double min, double max) {
        _vec_coerce_dispatcher(a, n, min, max);
    }
}

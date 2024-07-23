#pragma once

#ifdef __cplusplus
extern "C" {
#endif

double vec_sum(double* a, int n);
double vec_min(double* a, int n);
double vec_max(double* a, int n);
double vec_prod(double* a, int n);

double vec_mean(double* a, int n);
double vec_var(double* a, int n);
double vec_std(double* a, int n);

void vec_coerce(double* a, int n, double min, double max);

#ifdef __cplusplus
}
#endif

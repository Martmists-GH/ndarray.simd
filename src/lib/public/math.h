#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void vec_sqrt(double* a, int n);
void vec_pow(double* a, double b, int n);
void vec_ipow(double* a, double b, int n);
void vec_log(double* a, int n);
void vec_logbase(double* a, double b, int n);
void vec_exp(double* a, int n);

void vec_expm1(double* a, int n);
void vec_log1p(double* a, int n);
void vec_log2(double* a, int n);
void vec_log10(double* a, int n);

#ifdef __cplusplus
}
#endif

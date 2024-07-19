#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void vec_sqrt(double* arr, int n);
void vec_pow(double* arr, double b, int n);
void vec_ipow(double* arr, double b, int n);
void vec_log(double* arr, int n);
void vec_logbase(double* arr, double b, int n);
void vec_exp(double* arr, int n);

void vec_expm1(double* arr, int n);
void vec_log1p(double* arr, int n);
void vec_log2(double* arr, int n);
void vec_log10(double* arr, int n);

#ifdef __cplusplus
}
#endif

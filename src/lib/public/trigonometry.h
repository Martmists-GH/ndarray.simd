#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void vec_sin(double* arr, int n);
void vec_cos(double* arr, int n);
void vec_tan(double* arr, int n);
void vec_asin(double* arr, int n);
void vec_acos(double* arr, int n);
void vec_atan(double* arr, int n);
void vec_atan2(double* arr, double* b, int n);
void vec_sinh(double* arr, int n);
void vec_cosh(double* arr, int n);
void vec_tanh(double* arr, int n);
void vec_asinh(double* arr, int n);
void vec_acosh(double* arr, int n);
void vec_atanh(double* arr, int n);
void vec_hypot(double* arr, double* b, int n);

#ifdef __cplusplus
}
#endif

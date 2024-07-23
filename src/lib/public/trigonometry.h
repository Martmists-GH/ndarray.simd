#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void vec_sin(double* a, int n);
void vec_cos(double* a, int n);
void vec_tan(double* a, int n);
void vec_asin(double* a, int n);
void vec_acos(double* a, int n);
void vec_atan(double* a, int n);
void vec_atan2(double* a, double* b, int n);
void vec_sinh(double* a, int n);
void vec_cosh(double* a, int n);
void vec_tanh(double* a, int n);
void vec_asinh(double* a, int n);
void vec_acosh(double* a, int n);
void vec_atanh(double* a, int n);
void vec_hypot(double* a, double* b, int n);

#ifdef __cplusplus
}
#endif

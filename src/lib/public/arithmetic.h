#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void vec_add_vec(double* a, double* b, int n);
void vec_add_scalar(double* a, double b, int n);
void vec_sub_vec(double* a, double* b, int n);
void vec_sub_scalar(double* a, double b, int n);
void vec_mul_vec(double* a, double* b, int n);
void vec_mul_scalar(double* a, double b, int n);
void vec_div_vec(double* a, double* b, int n);
void vec_div_scalar(double* a, double b, int n);
void vec_negate(double* a, int n);
void vec_abs(double* a, int n);

#ifdef __cplusplus
}
#endif

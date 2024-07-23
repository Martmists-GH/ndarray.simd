#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void vec_and_vec(double* a, double* b, int n);
void vec_and_scalar(double* a, int b, int n);
void vec_or_vec(double* a, double* b, int n);
void vec_or_scalar(double* a, int b, int n);
void vec_xor_vec(double* a, double* b, int n);
void vec_xor_scalar(double* a, int b, int n);
void vec_not(double* a, int n);
void vec_lshift_vec(double* a, double* b, int n);
void vec_lshift_scalar(double* a, int b, int n);
void vec_rshift_vec(double* a, double* b, int n);
void vec_rshift_scalar(double* a, int b, int n);

#ifdef __cplusplus
}
#endif

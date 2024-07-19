#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void vec_and_vec(double* arr, double* b, int n);
void vec_and_scalar(double* arr, int b, int n);
void vec_or_vec(double* arr, double* b, int n);
void vec_or_scalar(double* arr, int b, int n);
void vec_xor_vec(double* arr, double* b, int n);
void vec_xor_scalar(double* arr, int b, int n);
void vec_not(double* arr, int n);
void vec_lshift_vec(double* arr, double* b, int n);
void vec_lshift_scalar(double* arr, int b, int n);
void vec_rshift_vec(double* arr, double* b, int n);
void vec_rshift_scalar(double* arr, int b, int n);

#ifdef __cplusplus
}
#endif

#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void vec_eq_vec(double* arr, double* b, int n);
void vec_eq_scalar(double* arr, double b, int n);
void vec_neq_vec(double* arr, double* b, int n);
void vec_neq_scalar(double* arr, double b, int n);
void vec_lt_vec(double* arr, double* b, int n);
void vec_lt_scalar(double* arr, double b, int n);
void vec_gt_vec(double* arr, double* b, int n);
void vec_gt_scalar(double* arr, double b, int n);

#ifdef __cplusplus
}
#endif

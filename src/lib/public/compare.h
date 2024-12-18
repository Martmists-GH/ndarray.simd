#pragma once

#include "stdbool.h"

#ifdef __cplusplus
extern "C" {
#endif

void vec_eq_vec(double* a, double* b, int n, double rtol, double atol, bool allow_nan);
void vec_eq_scalar(double* a, double b, int n, double rtol, double atol, bool allow_nan);
void vec_neq_vec(double* a, double* b, int n, double rtol, double atol, bool allow_nan);
void vec_neq_scalar(double* a, double b, int n, double rtol, double atol, bool allow_nan);
void vec_lt_vec(double* a, double* b, int n);
void vec_lt_scalar(double* a, double b, int n);
void vec_gt_vec(double* a, double* b, int n);
void vec_gt_scalar(double* a, double b, int n);
void vec_lte_vec(double* a, double* b, int n);
void vec_lte_scalar(double* a, double b, int n);
void vec_gte_vec(double* a, double* b, int n);
void vec_gte_scalar(double* a, double b, int n);
void vec_isnan(double* a, int n);
void vec_isinf(double* a, int n);
void vec_isfinite(double* a, int n);

#ifdef __cplusplus
}
#endif

#pragma once

#ifdef __cplusplus
extern "C" {
#endif

double vec_dot(double* a, double* b, int n);
void vec_matmul(double* a, double* b, double* c, int n, int m, int p);

#ifdef __cplusplus
}
#endif

#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void vec_copy(double* arr, double* b, int n);
int get_simd_size();

#ifdef __cplusplus
}
#endif

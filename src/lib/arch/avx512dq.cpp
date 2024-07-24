#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avx512dq>(xsimd::avx512dq, double *, double, int);

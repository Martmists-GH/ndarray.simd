#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avx512vbmi>(xsimd::avx512vbmi, double *, double, int);

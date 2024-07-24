#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avx512f>(xsimd::avx512f, double *, double, int);

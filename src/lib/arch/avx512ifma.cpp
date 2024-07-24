#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avx512ifma>(xsimd::avx512ifma, double *, double, int);

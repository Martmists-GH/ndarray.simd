#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::fma3<xsimd::avx2>>(xsimd::fma3<xsimd::avx2>, double *, double, int);
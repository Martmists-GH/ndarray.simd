#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::fma3<xsimd::avx>>(xsimd::fma3<xsimd::avx>, double *, double, int);

#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);

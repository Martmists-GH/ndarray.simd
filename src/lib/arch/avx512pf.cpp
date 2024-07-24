#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avx512pf>(xsimd::avx512pf, double *, double, int);

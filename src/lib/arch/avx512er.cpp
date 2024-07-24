#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avx512er>(xsimd::avx512er, double *, double, int);

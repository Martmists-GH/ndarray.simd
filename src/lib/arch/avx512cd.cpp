#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avx512cd>(xsimd::avx512cd, double *, double, int);

#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::fma4>(xsimd::fma4, double *, double, int);

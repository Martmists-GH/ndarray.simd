#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::ssse3>(xsimd::ssse3, double *, double, int);

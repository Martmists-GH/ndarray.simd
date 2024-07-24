#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::neon>(xsimd::neon, double *, double, int);

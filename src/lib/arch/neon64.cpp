#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::neon64>(xsimd::neon64, double *, double, int);

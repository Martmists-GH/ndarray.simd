#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::i8mm<xsimd::neon64>>(xsimd::i8mm<xsimd::neon64>, double *, double, int);

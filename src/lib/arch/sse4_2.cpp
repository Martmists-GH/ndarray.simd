#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
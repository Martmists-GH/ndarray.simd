#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);

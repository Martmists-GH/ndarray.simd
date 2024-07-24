#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avx512bw>(xsimd::avx512bw, double *, double, int);

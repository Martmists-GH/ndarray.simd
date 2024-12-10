#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::sse2>(xsimd::sse2, double *, double, int);
template void _vec_add_vec::operator()<xsimd::sse2>(xsimd::sse2, double *, double *, int);
template void _vec_sub_scalar::operator()<xsimd::sse2>(xsimd::sse2, double *, double, int);
template void _vec_sub_vec::operator()<xsimd::sse2>(xsimd::sse2, double *, double *, int);

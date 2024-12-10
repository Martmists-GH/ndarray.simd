#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avx>(xsimd::avx, double *, double, int);
template void _vec_add_vec::operator()<xsimd::avx>(xsimd::avx, double *, double *, int);
template void _vec_sub_scalar::operator()<xsimd::avx>(xsimd::avx, double *, double, int);
template void _vec_sub_vec::operator()<xsimd::avx>(xsimd::avx, double *, double *, int);

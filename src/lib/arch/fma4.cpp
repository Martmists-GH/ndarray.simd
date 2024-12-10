#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::fma4>(xsimd::fma4, double *, double, int);
template void _vec_add_vec::operator()<xsimd::fma4>(xsimd::fma4, double *, double *, int);
template void _vec_sub_scalar::operator()<xsimd::fma4>(xsimd::fma4, double *, double, int);
template void _vec_sub_vec::operator()<xsimd::fma4>(xsimd::fma4, double *, double *, int);

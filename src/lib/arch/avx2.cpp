#include "../cpp/arithmetic_priv.h"
#include "../cpp/bitwise_priv.h"

// Arithmetic
template void _vec_add_scalar::operator()<xsimd::avx2>(xsimd::avx2, double *, double, int);
template void _vec_add_vec::operator()<xsimd::avx2>(xsimd::avx2, double *, double *, int);
template void _vec_sub_scalar::operator()<xsimd::avx2>(xsimd::avx2, double *, double, int);
template void _vec_sub_vec::operator()<xsimd::avx2>(xsimd::avx2, double *, double *, int);
template void _vec_mul_scalar::operator()<xsimd::avx2>(xsimd::avx2, double *, double, int);
template void _vec_mul_vec::operator()<xsimd::avx2>(xsimd::avx2, double *, double *, int);
template void _vec_div_scalar::operator()<xsimd::avx2>(xsimd::avx2, double *, double, int);
template void _vec_div_vec::operator()<xsimd::avx2>(xsimd::avx2, double *, double *, int);
template void _vec_negate::operator()<xsimd::avx2>(xsimd::avx2, double *, int);
template void _vec_abs::operator()<xsimd::avx2>(xsimd::avx2, double *, int);

// Bitwise
template void _vec_and_scalar::operator()<xsimd::avx2>(xsimd::avx2, double *, int, int);
template void _vec_and_vec::operator()<xsimd::avx2>(xsimd::avx2, double *, double *, int);
template void _vec_or_scalar::operator()<xsimd::avx2>(xsimd::avx2, double *, int, int);
template void _vec_or_vec::operator()<xsimd::avx2>(xsimd::avx2, double *, double *, int);
template void _vec_xor_scalar::operator()<xsimd::avx2>(xsimd::avx2, double *, int, int);
template void _vec_xor_vec::operator()<xsimd::avx2>(xsimd::avx2, double *, double *, int);
template void _vec_not::operator()<xsimd::avx2>(xsimd::avx2, double *, int);
template void _vec_lshift_scalar::operator()<xsimd::avx2>(xsimd::avx2, double *, int, int);
template void _vec_lshift_vec::operator()<xsimd::avx2>(xsimd::avx2, double *, double *, int);
template void _vec_rshift_scalar::operator()<xsimd::avx2>(xsimd::avx2, double *, int, int);
template void _vec_rshift_vec::operator()<xsimd::avx2>(xsimd::avx2, double *, double *, int);

#include "../cpp/arithmetic_priv.h"
#include "../cpp/bitwise_priv.h"
#include "../cpp/compare_priv.h"

// Arithmetic
template void _vec_add_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_add_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_sub_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_sub_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_mul_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_mul_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_div_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_div_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_negate::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int);
template void _vec_abs::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int);

// Bitwise
template void _vec_and_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int, int);
template void _vec_and_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_or_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int, int);
template void _vec_or_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_xor_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int, int);
template void _vec_xor_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_not::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int);
template void _vec_lshift_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int, int);
template void _vec_lshift_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_rshift_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int, int);
template void _vec_rshift_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);

// Compare
template void _vec_eq_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_eq_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_neq_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_neq_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_lt_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_lt_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_gt_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_gt_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_lte_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_lte_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_gte_scalar::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double, int);
template void _vec_gte_vec::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, double *, int);
template void _vec_isnan::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int);
template void _vec_isinf::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int);
template void _vec_isfinite::operator()<xsimd::sse4_2>(xsimd::sse4_2, double *, int);

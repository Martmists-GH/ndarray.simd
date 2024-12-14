#include "../cpp/arithmetic_priv.h"
#include "../cpp/bitwise_priv.h"
#include "../cpp/compare_priv.h"
#include "../cpp/math_priv.h"
#include "../cpp/misc_priv.h"
#include "../cpp/procedure_priv.h"
#include "../cpp/rounding_priv.h"
#include "../cpp/trigonometry_priv.h"
#include "../cpp/vector_priv.h"

// Arithmetic
template void _vec_add_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_add_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_sub_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_sub_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_mul_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_mul_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_div_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_div_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_negate::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_abs::operator()<xsimd::sse3>(xsimd::sse3, double *, int);

// Bitwise
template void _vec_and_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, int, int);
template void _vec_and_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_or_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, int, int);
template void _vec_or_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_xor_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, int, int);
template void _vec_xor_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_not::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_lshift_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, int, int);
template void _vec_lshift_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_rshift_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, int, int);
template void _vec_rshift_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);

// Compare
template void _vec_eq_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_eq_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_neq_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_neq_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_lt_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_lt_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_gt_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_gt_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_lte_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_lte_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_gte_scalar::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_gte_vec::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_isnan::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_isinf::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_isfinite::operator()<xsimd::sse3>(xsimd::sse3, double *, int);

// Math
template void _vec_sqrt::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_pow::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_ipow::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_log::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_logbase::operator()<xsimd::sse3>(xsimd::sse3, double *, double, int);
template void _vec_exp::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_expm1::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_log1p::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_log2::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_log10::operator()<xsimd::sse3>(xsimd::sse3, double *, int);

// Misc
template void _vec_copy::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template int _get_simd_size::operator()<xsimd::sse3>(xsimd::sse3);

// Procedure
template double _vec_sum::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template double _vec_min::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template double _vec_max::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template double _vec_prod::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template double _vec_var::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_coerce::operator()<xsimd::sse3>(xsimd::sse3, double *, int, double, double);

// Rounding
template void _vec_floor::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_ceil::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_trunc::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_round::operator()<xsimd::sse3>(xsimd::sse3, double *, int);

// Trigonometry
template void _vec_sin::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_cos::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_tan::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_asin::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_acos::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_atan::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_atan2::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_sinh::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_cosh::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_tanh::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_asinh::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_acosh::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_atanh::operator()<xsimd::sse3>(xsimd::sse3, double *, int);
template void _vec_hypot::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);

// Vector
template double _vec_dot::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, int);
template void _vec_matmul::operator()<xsimd::sse3>(xsimd::sse3, double *, double *, double *, int, int, int);

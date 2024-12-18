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
template void _vec_add_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_add_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_sub_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_sub_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_mul_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_mul_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_div_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_div_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_rem_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_rem_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_negate::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_abs::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);

// Bitwise
template void _vec_and_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int, int);
template void _vec_and_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_or_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int, int);
template void _vec_or_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_xor_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int, int);
template void _vec_xor_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_not::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_lshift_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int, int);
template void _vec_lshift_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_rshift_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int, int);
template void _vec_rshift_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);

// Compare
template void _vec_eq_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int, double, double, bool);
template void _vec_eq_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int, double, double, bool);
template void _vec_neq_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int, double, double, bool);
template void _vec_neq_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int, double, double, bool);
template void _vec_lt_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_lt_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_gt_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_gt_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_lte_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_lte_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_gte_scalar::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_gte_vec::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_isnan::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_isinf::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_isfinite::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);

// Math
template void _vec_sqrt::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_pow::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_ipow::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_log::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_logbase::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double, int);
template void _vec_exp::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_expm1::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_log1p::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_log2::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_log10::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);

// Misc
template void _vec_copy::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template int _get_simd_size::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>);

// Procedure
template double _vec_sum::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template double _vec_min::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template double _vec_max::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template double _vec_prod::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template double _vec_var::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_coerce::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int, double, double);

// Rounding
template void _vec_floor::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_ceil::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_trunc::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_round::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);

// Trigonometry
template void _vec_sin::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_cos::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_tan::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_asin::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_acos::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_atan::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_atan2::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_sinh::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_cosh::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_tanh::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_asinh::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_acosh::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_atanh::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, int);
template void _vec_hypot::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);

// Vector
template double _vec_dot::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, int);
template void _vec_matmul::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, double *, double *, double *, int, int, int);

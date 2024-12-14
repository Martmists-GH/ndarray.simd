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
template void _vec_add_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_add_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_sub_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_sub_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_mul_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_mul_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_div_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_div_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_rem_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_rem_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_negate::operator()<Type>(Type, double *, int);
template void _vec_abs::operator()<Type>(Type, double *, int);

// Bitwise
template void _vec_and_scalar::operator()<Type>(Type, double *, int, int);
template void _vec_and_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_or_scalar::operator()<Type>(Type, double *, int, int);
template void _vec_or_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_xor_scalar::operator()<Type>(Type, double *, int, int);
template void _vec_xor_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_not::operator()<Type>(Type, double *, int);
template void _vec_lshift_scalar::operator()<Type>(Type, double *, int, int);
template void _vec_lshift_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_rshift_scalar::operator()<Type>(Type, double *, int, int);
template void _vec_rshift_vec::operator()<Type>(Type, double *, double *, int);

// Compare
template void _vec_eq_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_eq_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_neq_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_neq_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_lt_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_lt_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_gt_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_gt_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_lte_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_lte_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_gte_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_gte_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_isnan::operator()<Type>(Type, double *, int);
template void _vec_isinf::operator()<Type>(Type, double *, int);
template void _vec_isfinite::operator()<Type>(Type, double *, int);

// Math
template void _vec_sqrt::operator()<Type>(Type, double *, int);
template void _vec_pow::operator()<Type>(Type, double *, double, int);
template void _vec_ipow::operator()<Type>(Type, double *, double, int);
template void _vec_log::operator()<Type>(Type, double *, int);
template void _vec_logbase::operator()<Type>(Type, double *, double, int);
template void _vec_exp::operator()<Type>(Type, double *, int);
template void _vec_expm1::operator()<Type>(Type, double *, int);
template void _vec_log1p::operator()<Type>(Type, double *, int);
template void _vec_log2::operator()<Type>(Type, double *, int);
template void _vec_log10::operator()<Type>(Type, double *, int);

// Misc
template void _vec_copy::operator()<Type>(Type, double *, double *, int);
template int _get_simd_size::operator()<Type>(Type);

// Procedure
template double _vec_sum::operator()<Type>(Type, double *, int);
template double _vec_min::operator()<Type>(Type, double *, int);
template double _vec_max::operator()<Type>(Type, double *, int);
template double _vec_prod::operator()<Type>(Type, double *, int);
template double _vec_var::operator()<Type>(Type, double *, int);
template void _vec_coerce::operator()<Type>(Type, double *, int, double, double);

// Rounding
template void _vec_floor::operator()<Type>(Type, double *, int);
template void _vec_ceil::operator()<Type>(Type, double *, int);
template void _vec_trunc::operator()<Type>(Type, double *, int);
template void _vec_round::operator()<Type>(Type, double *, int);

// Trigonometry
template void _vec_sin::operator()<Type>(Type, double *, int);
template void _vec_cos::operator()<Type>(Type, double *, int);
template void _vec_tan::operator()<Type>(Type, double *, int);
template void _vec_asin::operator()<Type>(Type, double *, int);
template void _vec_acos::operator()<Type>(Type, double *, int);
template void _vec_atan::operator()<Type>(Type, double *, int);
template void _vec_atan2::operator()<Type>(Type, double *, double *, int);
template void _vec_sinh::operator()<Type>(Type, double *, int);
template void _vec_cosh::operator()<Type>(Type, double *, int);
template void _vec_tanh::operator()<Type>(Type, double *, int);
template void _vec_asinh::operator()<Type>(Type, double *, int);
template void _vec_acosh::operator()<Type>(Type, double *, int);
template void _vec_atanh::operator()<Type>(Type, double *, int);
template void _vec_hypot::operator()<Type>(Type, double *, double *, int);

// Vector
template double _vec_dot::operator()<Type>(Type, double *, double *, int);
template void _vec_matmul::operator()<Type>(Type, double *, double *, double *, int, int, int);

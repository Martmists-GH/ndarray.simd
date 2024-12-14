#include "../cpp/arithmetic_priv.h"
#include "../cpp/bitwise_priv.h"

// Arithmetic
template void _vec_add_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_add_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_sub_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_sub_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_mul_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_mul_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_div_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_div_vec::operator()<Type>(Type, double *, double *, int);
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

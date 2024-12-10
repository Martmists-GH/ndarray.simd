#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_add_vec::operator()<Type>(Type, double *, double *, int);
template void _vec_sub_scalar::operator()<Type>(Type, double *, double, int);
template void _vec_sub_vec::operator()<Type>(Type, double *, double *, int);

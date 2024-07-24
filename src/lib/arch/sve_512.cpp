#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::detail::sve<512>>(xsimd::detail::sve<512>, double *, double, int);

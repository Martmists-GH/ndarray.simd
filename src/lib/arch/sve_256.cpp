#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::detail::sve<256>>(xsimd::detail::sve<256>, double *, double, int);

#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::detail::sve<128>>(xsimd::detail::sve<128>, double *, double, int);

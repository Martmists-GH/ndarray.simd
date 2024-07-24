#include "../cpp/arithmetic_priv.h"

template void _vec_add_scalar::operator()<xsimd::avxvnni>(xsimd::avxvnni, double *, double, int);

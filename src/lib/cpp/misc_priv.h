#pragma once

#include "common.h"

MAKE_SIMD(void, _vec_copy, double* a, double* b, int n) {
    using batch = xsimd::batch<double, Arch>;

    std::size_t size = n - n % batch::size;

    for (std::size_t i = 0; i < size; i += batch::size) {
        auto vb = batch::load_unaligned(&b[i]);
        xsimd::store_unaligned(&a[i], vb);
    }

    for (std::size_t i = size; i < n; ++i) {
        a[i] = b[i];
    }
}

struct _get_simd_size { 
    template <class Arch> int operator()(Arch); 
};
static auto _get_simd_size_dispatcher = xsimd::dispatch<arch_list>(_get_simd_size{});
#if defined(__x86_64__)
extern template int _get_simd_size::operator()<xsimd::fma3<xsimd::avx2>>(xsimd::fma3<xsimd::avx2>); 
extern template int _get_simd_size::operator()<xsimd::avx2>(xsimd::avx2);
extern template int _get_simd_size::operator()<xsimd::fma3<xsimd::avx>>(xsimd::fma3<xsimd::avx>);
extern template int _get_simd_size::operator()<xsimd::avx>(xsimd::avx);
extern template int _get_simd_size::operator()<xsimd::fma4>(xsimd::fma4);
extern template int _get_simd_size::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>);
extern template int _get_simd_size::operator()<xsimd::sse4_2>(xsimd::sse4_2);
extern template int _get_simd_size::operator()<xsimd::sse4_1>(xsimd::sse4_1);
extern template int _get_simd_size::operator()<xsimd::ssse3>(xsimd::ssse3);
extern template int _get_simd_size::operator()<xsimd::sse3>(xsimd::sse3);
extern template int _get_simd_size::operator()<xsimd::sse2>(xsimd::sse2);
#elif defined(__aarch64__)
extern template int _get_simd_size::operator()<xsimd::neon64>(xsimd::neon64);
#endif
template <class Arch> int _get_simd_size::operator()(Arch) {
    using batch = xsimd::batch<double, Arch>;
    return batch::size;
}

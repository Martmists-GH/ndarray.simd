#pragma once

#include <xsimd/xsimd.hpp>
#include "lib.h"

constexpr std::size_t simd_size = xsimd::batch<double>::size;

#if defined(__x86_64__)
using arch_list = xsimd::arch_list<
    xsimd::avx512vnni<xsimd::avx512vbmi>,
    xsimd::avx512vbmi,
    xsimd::avx512ifma,
    xsimd::avx512pf,
    xsimd::avx512vnni<xsimd::avx512bw>,
    xsimd::avx512bw,
    xsimd::avx512er,
    xsimd::avx512dq,
    xsimd::avx512cd,
    xsimd::avx512f,
    // xsimd::avxvnni,
    xsimd::fma3<xsimd::avx2>,
    xsimd::avx2,
    xsimd::fma3<xsimd::avx>,
    xsimd::avx,
    xsimd::fma4,
    xsimd::fma3<xsimd::sse4_2>,
    xsimd::sse4_2,
    xsimd::sse4_1,
    xsimd::ssse3,
    xsimd::sse3,
    xsimd::sse2
>;
#elif defined(__aarch64__)
using arch_list = xsimd::arch_list<
    xsimd::detail::sve_vector_type<double>,
    xsimd::neon64
>;
#else
#error "Unsupported architecture"
#endif

#if defined(__x86_64__)
#define MAKE_SIMD(ret, name, ...) struct name {                                                                                \
    template <class Arch>                                                                                                      \
    ret operator()(Arch, __VA_ARGS__);                                                                                         \
};                                                                                                                             \
static auto name##_dispatcher = xsimd::dispatch<arch_list>(name{});                                                            \
extern template ret name::operator()<xsimd::avx512vnni<xsimd::avx512vbmi>>(xsimd::avx512vnni<xsimd::avx512vbmi>, __VA_ARGS__); \
extern template ret name::operator()<xsimd::avx512vbmi>(xsimd::avx512vbmi, __VA_ARGS__);                                       \
extern template ret name::operator()<xsimd::avx512ifma>(xsimd::avx512ifma, __VA_ARGS__);                                       \
extern template ret name::operator()<xsimd::avx512pf>(xsimd::avx512pf, __VA_ARGS__);                                           \
extern template ret name::operator()<xsimd::avx512vnni<xsimd::avx512bw>>(xsimd::avx512vnni<xsimd::avx512bw>, __VA_ARGS__);     \
extern template ret name::operator()<xsimd::avx512bw>(xsimd::avx512bw, __VA_ARGS__);                                           \
extern template ret name::operator()<xsimd::avx512er>(xsimd::avx512er, __VA_ARGS__);                                           \
extern template ret name::operator()<xsimd::avx512dq>(xsimd::avx512dq, __VA_ARGS__);                                           \
extern template ret name::operator()<xsimd::avx512cd>(xsimd::avx512cd, __VA_ARGS__);                                           \
extern template ret name::operator()<xsimd::avx512f>(xsimd::avx512f, __VA_ARGS__);                                             \
extern template ret name::operator()<xsimd::avxvnni>(xsimd::avxvnni, __VA_ARGS__);                                             \
extern template ret name::operator()<xsimd::fma3<xsimd::avx2>>(xsimd::fma3<xsimd::avx2>, __VA_ARGS__);                         \
extern template ret name::operator()<xsimd::avx2>(xsimd::avx2, __VA_ARGS__);                                                   \
extern template ret name::operator()<xsimd::fma3<xsimd::avx>>(xsimd::fma3<xsimd::avx>, __VA_ARGS__);                           \
extern template ret name::operator()<xsimd::avx>(xsimd::avx, __VA_ARGS__);                                                     \
extern template ret name::operator()<xsimd::fma4>(xsimd::fma4, __VA_ARGS__);                                                   \
extern template ret name::operator()<xsimd::fma3<xsimd::sse4_2>>(xsimd::fma3<xsimd::sse4_2>, __VA_ARGS__);                     \
extern template ret name::operator()<xsimd::sse4_2>(xsimd::sse4_2, __VA_ARGS__);                                               \
extern template ret name::operator()<xsimd::sse4_1>(xsimd::sse4_1, __VA_ARGS__);                                               \
extern template ret name::operator()<xsimd::ssse3>(xsimd::ssse3, __VA_ARGS__);                                                 \
extern template ret name::operator()<xsimd::sse3>(xsimd::sse3, __VA_ARGS__);                                                   \
extern template ret name::operator()<xsimd::sse2>(xsimd::sse2, __VA_ARGS__);                                                   \
template <class Arch>                                                                                                          \
ret name::operator()(Arch, __VA_ARGS__)
#elif defined(__aarch64__)
#define MAKE_SIMD(ret, name, ...) struct name {                                                                                     \
    template <class Arch>                                                                                                           \
    ret operator()(Arch, __VA_ARGS__);                                                                                              \
};                                                                                                                                  \
static auto name##_dispatcher = xsimd::dispatch<arch_list>(name{});                                                                 \
extern template ret name::operator()<xsimd::detail::sve_vector_type<double>>(xsimd::detail::sve_vector_type<double>, __VA_ARGS__);  \
extern template ret name::operator()<xsimd::neon64>(xsimd::neon64, __VA_ARGS__);                                                    \
template <class Arch>                                                                                                               \
ret name::operator()(Arch, __VA_ARGS__)
#endif

#define MAKE_TRUE() xsimd::batch(1.0)
#define MAKE_FALSE() xsimd::batch(0.0)

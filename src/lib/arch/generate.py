archs = [
    "xsimd::fma3<xsimd::avx2>",
    "xsimd::avx2",
    "xsimd::fma3<xsimd::avx>",
    "xsimd::avx",
    "xsimd::fma4",
    "xsimd::fma3<xsimd::sse4_2>",
    "xsimd::sse4_2",
    "xsimd::sse4_1",
    "xsimd::ssse3",
    "xsimd::sse3",
    "xsimd::sse2",
    "xsimd::neon64",
]

with open("template.cpp", "r") as fp:
    template = fp.read()

for arch in archs:
    arch_name = arch.replace("<", "_").replace(">", "").replace("xsimd::", "").replace("detail::", "")
    with open(f"{arch_name}.cpp", "w") as fp:
        fp.write(template.replace("Type", arch))

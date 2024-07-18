package com.martmists.ndarray.simd

import java.io.File

internal actual object NativeSpeedup {
    init {
        val osName = System.getProperty("os.name")
        val platform = when {
            osName.startsWith("Linux") -> "linux"
            osName.startsWith("Mac") -> "macos"
            osName.startsWith("Windows") -> "windows"
            else -> throw UnsupportedOperationException("Unsupported platform: $osName")
        }
        val arch = when (val osArch = System.getProperty("os.arch")) {
            "x86_64", "amd64" -> "X64"
            "aarch64" -> "Arm64"
            else -> throw UnsupportedOperationException("Unsupported architecture: $osArch")
        }

        val tmp = File.createTempFile("libndarray_simd", ".so")
        tmp.deleteOnExit()

        val lib = NativeSpeedup::class.java.getResourceAsStream("/META-INF/natives/$platform$arch/libndarray_simd.so")!!
        lib.copyTo(tmp.outputStream())

        System.load(tmp.absolutePath)
    }

    actual external fun vecAddVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecAddScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun vecSubVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecSubScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun vecMulVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecMulScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun vecDivVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecDivScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun vecNegate(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecAbs(a: DoubleArray, aOffset: Int, aSize: Int)

    actual external fun vecAndVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecAndScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)
    actual external fun vecOrVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecOrScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)
    actual external fun vecXorVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecXorScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)
    actual external fun vecNot(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecLShiftVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecLShiftScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)
    actual external fun vecRShiftVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecRShiftScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)

    actual external fun vecEqVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecEqScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun vecNeqVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecNeqScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun vecLtVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecLtScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun vecGtVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecGtScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)

    actual external fun vecSqrt(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecPow(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun veciPow(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun vecLog(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecLogBase(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    actual external fun vecExp(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecExpm1(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecLog1p(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecLog2(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecLog10(a: DoubleArray, aOffset: Int, aSize: Int)

    actual external fun vecCopy(dest: DoubleArray, destOffset: Int, destSize: Int, src: DoubleArray, srcOffset: Int)
    actual external fun getSimdSize(): Int

    actual external fun vecSum(a: DoubleArray, aOffset: Int, aSize: Int): Double
    actual external fun vecMin(a: DoubleArray, aOffset: Int, aSize: Int): Double
    actual external fun vecMax(a: DoubleArray, aOffset: Int, aSize: Int): Double
    actual external fun vecProduct(a: DoubleArray, aOffset: Int, aSize: Int): Double
    actual external fun vecMean(a: DoubleArray, aOffset: Int, aSize: Int): Double
    actual external fun vecVariance(a: DoubleArray, aOffset: Int, aSize: Int): Double
    actual external fun vecStdDev(a: DoubleArray, aOffset: Int, aSize: Int): Double
    actual external fun vecCoerce(a: DoubleArray, aOffset: Int, aSize: Int, min: Double, max: Double)

    actual external fun vecSin(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecCos(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecTan(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecAsin(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecAcos(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecAtan(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecAtan2(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    actual external fun vecSinh(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecCosh(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecTanh(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecAsinh(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecAcosh(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecAtanh(a: DoubleArray, aOffset: Int, aSize: Int)
    actual external fun vecHypot(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)

    actual external fun vecDot(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int): Double
    actual external fun vecMatMul(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int, n: Int, m: Int, p: Int): DoubleArray
}

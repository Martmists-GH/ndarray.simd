package com.martmists.ndarray.simd

internal expect object NativeSpeedup {
    fun vecAddVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecAddScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecSubVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecSubScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecMulVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecMulScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecDivVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecDivScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecRemVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecRemScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecNegate(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecAbs(a: DoubleArray, aOffset: Int, aSize: Int)

    fun vecAndVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecAndScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)
    fun vecOrVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecOrScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)
    fun vecXorVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecXorScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)
    fun vecNot(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecLShiftVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecLShiftScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)
    fun vecRShiftVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecRShiftScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int)

    fun vecEqVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecEqScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecNeqVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecNeqScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecLtVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecLtScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecLteVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecLteScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecGtVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecGtScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecGteVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecGteScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)

    fun vecIsNan(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecIsInf(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecIsFinite(a: DoubleArray, aOffset: Int, aSize: Int)

    fun vecSqrt(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecPow(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun veciPow(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecLog(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecLogBase(a: DoubleArray, aOffset: Int, aSize: Int, b: Double)
    fun vecExp(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecExpm1(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecLog1p(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecLog2(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecLog10(a: DoubleArray, aOffset: Int, aSize: Int)

    fun vecCopy(dest: DoubleArray, destOffset: Int, destSize: Int, src: DoubleArray, srcOffset: Int)
    fun getSimdSize(): Int

    fun vecSum(a: DoubleArray, aOffset: Int, aSize: Int): Double
    fun vecMin(a: DoubleArray, aOffset: Int, aSize: Int): Double
    fun vecMax(a: DoubleArray, aOffset: Int, aSize: Int): Double
    fun vecProduct(a: DoubleArray, aOffset: Int, aSize: Int): Double
    fun vecMean(a: DoubleArray, aOffset: Int, aSize: Int): Double
    fun vecVariance(a: DoubleArray, aOffset: Int, aSize: Int): Double
    fun vecStdDev(a: DoubleArray, aOffset: Int, aSize: Int): Double
    fun vecCoerce(a: DoubleArray, aOffset: Int, aSize: Int, min: Double, max: Double)

    fun vecFloor(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecCeil(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecTrunc(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecRound(a: DoubleArray, aOffset: Int, aSize: Int)

    fun vecSin(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecCos(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecTan(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecAsin(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecAcos(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecAtan(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecAtan2(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)
    fun vecSinh(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecCosh(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecTanh(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecAsinh(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecAcosh(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecAtanh(a: DoubleArray, aOffset: Int, aSize: Int)
    fun vecHypot(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int)

    fun vecDot(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int): Double
    fun vecMatMul(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int, n: Int, m: Int, p: Int): DoubleArray
}

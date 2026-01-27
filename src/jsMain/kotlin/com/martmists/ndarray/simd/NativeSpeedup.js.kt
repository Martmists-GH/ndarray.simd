package com.martmists.ndarray.simd

internal actual object NativeSpeedup {
    actual fun getSimdSize() = 8
    actual fun getSimdAvailable() = false
    actual fun vecAddVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecAddScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecSubVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecSubScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecMulVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecMulScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecDivVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecDivScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecRemVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecRemScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecNegate(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecAbs(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecAndVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecAndScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {}
    actual fun vecOrVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecOrScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {}
    actual fun vecXorVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecXorScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {}
    actual fun vecNot(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecLShiftVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecLShiftScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {}
    actual fun vecRShiftVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecRShiftScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {}
    actual fun vecEqVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int, rtol: Double, atol: Double, allowNan: Boolean) {}
    actual fun vecEqScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double, rtol: Double, atol: Double, allowNan: Boolean) {}
    actual fun vecNeqVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int, rtol: Double, atol: Double, allowNan: Boolean) {}
    actual fun vecNeqScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double, rtol: Double, atol: Double, allowNan: Boolean) {}
    actual fun vecLtVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecLtScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecLteVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecLteScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecGtVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecGtScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecGteVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecGteScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecIsNan(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecIsInf(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecIsFinite(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecSqrt(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecPow(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun veciPow(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecLog(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecLogBase(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {}
    actual fun vecExp(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecExpm1(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecLog1p(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecLog2(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecLog10(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecCopy(dest: DoubleArray, destOffset: Int, destSize: Int, src: DoubleArray, srcOffset: Int) {}
    actual fun vecSum(a: DoubleArray, aOffset: Int, aSize: Int): Double = TODO("Not yet implemented")
    actual fun vecMin(a: DoubleArray, aOffset: Int, aSize: Int): Double = TODO("Not yet implemented")
    actual fun vecMax(a: DoubleArray, aOffset: Int, aSize: Int): Double = TODO("Not yet implemented")
    actual fun vecProduct(a: DoubleArray, aOffset: Int, aSize: Int): Double = TODO("Not yet implemented")
    actual fun vecMean(a: DoubleArray, aOffset: Int, aSize: Int): Double = TODO("Not yet implemented")
    actual fun vecVariance(a: DoubleArray, aOffset: Int, aSize: Int): Double = TODO("Not yet implemented")
    actual fun vecStdDev(a: DoubleArray, aOffset: Int, aSize: Int): Double = TODO("Not yet implemented")
    actual fun vecCoerce(a: DoubleArray, aOffset: Int, aSize: Int, min: Double, max: Double) {}
    actual fun vecFloor(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecCeil(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecTrunc(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecRound(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecSin(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecCos(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecTan(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecAsin(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecAcos(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecAtan(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecAtan2(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecSinh(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecCosh(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecTanh(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecAsinh(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecAcosh(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecAtanh(a: DoubleArray, aOffset: Int, aSize: Int) {}
    actual fun vecHypot(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {}
    actual fun vecDot(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int): Double = TODO("Not yet implemented")
    actual fun vecMatMul(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int, n: Int, m: Int, p: Int): DoubleArray = TODO("Not yet implemented")
}

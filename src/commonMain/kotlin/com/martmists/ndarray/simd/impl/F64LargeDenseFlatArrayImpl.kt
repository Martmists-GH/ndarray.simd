@file:Suppress("DEPRECATION")

package com.martmists.ndarray.simd.impl

import com.martmists.ndarray.simd.F64Array
import com.martmists.ndarray.simd.NativeSpeedup

internal class F64LargeDenseFlatArrayImpl(
    data: DoubleArray,
    offset: Int,
    size: Int
) : F64DenseFlatArrayBase(data, offset, size) {
    override fun dot(other: F64Array): Double {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            return NativeSpeedup.vecDot(data, offset, length, other.data, other.offset)
        } else {
            return super.dot(other)
        }
    }
    override fun sum() = NativeSpeedup.vecSum(data, offset, length)
    override fun max() = NativeSpeedup.vecMax(data, offset, length)
    override fun min() = NativeSpeedup.vecMin(data, offset, length)
    override fun product() = NativeSpeedup.vecProduct(data, offset, length)
    override fun mean() = NativeSpeedup.vecMean(data, offset, length)
    override fun variance() = NativeSpeedup.vecVariance(data, offset, length)
    override fun stdDev() = NativeSpeedup.vecStdDev(data, offset, length)
    override fun coerceInPlace(min: Double, max: Double) = NativeSpeedup.vecCoerce(data, offset, length, min, max)

    override fun expInPlace() = NativeSpeedup.vecExp(data, offset, length)
    override fun expm1InPlace() = NativeSpeedup.vecExpm1(data, offset, length)
    override fun logInPlace() = NativeSpeedup.vecLog(data, offset, length)
    override fun log1pInPlace() = NativeSpeedup.vecLog1p(data, offset, length)
    override fun log2InPlace() = NativeSpeedup.vecLog2(data, offset, length)
    override fun log10InPlace() = NativeSpeedup.vecLog10(data, offset, length)
    override fun logBaseInPlace(base: Double) = NativeSpeedup.vecLogBase(data, offset, length, base)
    override fun sqrtInPlace() = NativeSpeedup.vecSqrt(data, offset, length)
    override fun powInPlace(power: Double) = NativeSpeedup.vecPow(data, offset, length, power)
    override fun expBaseInPlace(base: Double) = NativeSpeedup.veciPow(data, offset, length, base)

    override fun unaryMinusInPlace() = NativeSpeedup.vecNegate(data, offset, length)
    override fun plusAssign(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecAddVec(data, offset, length, other.data, other.offset)
        } else {
            super.plusAssign(other)
        }
    }
    override fun plusAssign(other: Double) = NativeSpeedup.vecAddScalar(data, offset, length, other)
    override fun minusAssign(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecSubVec(data, offset, length, other.data, other.offset)
        } else {
            super.minusAssign(other)
        }
    }
    override fun minusAssign(other: Double) = NativeSpeedup.vecSubScalar(data, offset, length, other)
    override fun timesAssign(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecMulVec(data, offset, length, other.data, other.offset)
        } else {
            super.timesAssign(other)
        }
    }
    override fun timesAssign(other: Double) = NativeSpeedup.vecMulScalar(data, offset, length, other)
    override fun divAssign(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecDivVec(data, offset, length, other.data, other.offset)
        } else {
            super.divAssign(other)
        }
    }
    override fun divAssign(other: Double) = NativeSpeedup.vecDivScalar(data, offset, length, other)
    override fun remAssign(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecRemVec(data, offset, length, other.data, other.offset)
        } else {
            super.remAssign(other)
        }
    }
    override fun remAssign(other: Double) = NativeSpeedup.vecRemScalar(data, offset, length, other)
    override fun absInPlace() = NativeSpeedup.vecAbs(data, offset, length)

    override fun ltInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecLtVec(data, offset, length, other.data, other.offset)
        } else {
            super.ltInPlace(other)
        }
    }
    override fun ltInPlace(other: Double) = NativeSpeedup.vecLtScalar(data, offset, length, other)

    override fun lteInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecLteVec(data, offset, length, other.data, other.offset)
        } else {
            super.lteInPlace(other)
        }
    }
    override fun lteInPlace(other: Double) = NativeSpeedup.vecLteScalar(data, offset, length, other)

    override fun gtInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecGtVec(data, offset, length, other.data, other.offset)
        } else {
            super.gtInPlace(other)
        }
    }
    override fun gtInPlace(other: Double) = NativeSpeedup.vecGtScalar(data, offset, length, other)

    override fun gteInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecGteVec(data, offset, length, other.data, other.offset)
        } else {
            super.gteInPlace(other)
        }
    }
    override fun gteInPlace(other: Double) = NativeSpeedup.vecGteScalar(data, offset, length, other)

    override fun eqInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            val (rtol, atol) = F64Array.tolerance
            val allowNan = F64Array.equalNan
            NativeSpeedup.vecEqVec(data, offset, length, other.data, other.offset, rtol, atol, allowNan)
        } else {
            super.eqInPlace(other)
        }
    }
    override fun eqInPlace(other: Double) {
        val (rtol, atol) = F64Array.tolerance
        val allowNan = F64Array.equalNan && other.isNaN()
        NativeSpeedup.vecEqScalar(data, offset, length, other, rtol, atol, allowNan)
    }

    override fun neqInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            val (rtol, atol) = F64Array.tolerance
            val allowNan = F64Array.equalNan
            NativeSpeedup.vecNeqVec(data, offset, length, other.data, other.offset, rtol, atol, allowNan)
        } else {
            super.neqInPlace(other)
        }
    }
    override fun neqInPlace(other: Double) {
        val (rtol, atol) = F64Array.tolerance
        val allowNan = F64Array.equalNan && other.isNaN()
        NativeSpeedup.vecNeqScalar(data, offset, length, other, rtol, atol, allowNan)
    }

    override fun isNanInPlace() = NativeSpeedup.vecIsNan(data, offset, length)
    override fun isInfInPlace() = NativeSpeedup.vecIsInf(data, offset, length)
    override fun isFiniteInPlace() = NativeSpeedup.vecIsFinite(data, offset, length)

    override fun andInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecAndVec(data, offset, length, other.data, other.offset)
        } else {
            super.andInPlace(other)
        }
    }
    override fun andInPlace(other: Int) = NativeSpeedup.vecAndScalar(data, offset, length, other)
    override fun orInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecOrVec(data, offset, length, other.data, other.offset)
        } else {
            super.orInPlace(other)
        }
    }
    override fun orInPlace(other: Int) = NativeSpeedup.vecOrScalar(data, offset, length, other)
    override fun xorInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecXorVec(data, offset, length, other.data, other.offset)
        } else {
            super.xorInPlace(other)
        }
    }
    override fun xorInPlace(other: Int) = NativeSpeedup.vecXorScalar(data, offset, length, other)
    override fun notInPlace() = NativeSpeedup.vecNot(data, offset, length)
    override fun shlInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecLShiftVec(data, offset, length, other.data, other.offset)
        } else {
            super.shlInPlace(other)
        }
    }
    override fun shlInPlace(other: Int) = NativeSpeedup.vecLShiftScalar(data, offset, length, other)
    override fun shrInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecRShiftVec(data, offset, length, other.data, other.offset)
        } else {
            super.shrInPlace(other)
        }
    }
    override fun shrInPlace(other: Int) = NativeSpeedup.vecRShiftScalar(data, offset, length, other)

    override fun floorInPlace() = NativeSpeedup.vecFloor(data, offset, length)
    override fun ceilInPlace() = NativeSpeedup.vecCeil(data, offset, length)
    override fun truncInPlace() = NativeSpeedup.vecTrunc(data, offset, length)
    override fun roundInPlace() = NativeSpeedup.vecRound(data, offset, length)

    override fun sinInPlace() = NativeSpeedup.vecSin(data, offset, length)
    override fun cosInPlace() = NativeSpeedup.vecCos(data, offset, length)
    override fun tanInPlace() = NativeSpeedup.vecTan(data, offset, length)
    override fun asinInPlace() = NativeSpeedup.vecAsin(data, offset, length)
    override fun acosInPlace() = NativeSpeedup.vecAcos(data, offset, length)
    override fun atanInPlace() = NativeSpeedup.vecAtan(data, offset, length)
    override fun atan2InPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecAtan2(data, offset, length, other.data, other.offset)
        } else {
            super.atan2InPlace(other)
        }
    }
    override fun sinhInPlace() = NativeSpeedup.vecSinh(data, offset, length)
    override fun coshInPlace() = NativeSpeedup.vecCosh(data, offset, length)
    override fun tanhInPlace() = NativeSpeedup.vecTanh(data, offset, length)
    override fun asinhInPlace() = NativeSpeedup.vecAsinh(data, offset, length)
    override fun acoshInPlace() = NativeSpeedup.vecAcosh(data, offset, length)
    override fun atanhInPlace() = NativeSpeedup.vecAtanh(data, offset, length)
    override fun hypotInPlace(other: F64Array) {
        if (other is F64LargeDenseFlatArrayImpl) {
            checkShape(other)
            NativeSpeedup.vecHypot(data, offset, length, other.data, other.offset)
        } else {
            super.hypotInPlace(other)
        }
    }
}

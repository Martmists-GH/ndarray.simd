package com.martmists.ndarray.simd

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object NativeSpeedup {
    actual fun getSimdAvailable() = true

    actual fun vecAddVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_add_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecAddScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_add_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecSubVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_sub_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecSubScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_sub_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecMulVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_mul_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecMulScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_mul_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecDivVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_div_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecDivScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_div_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecRemVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_rem_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecRemScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_rem_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecNegate(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_negate(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecAbs(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_abs(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecAndVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_and_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecAndScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {
        a.usePinned { pinA ->
            simd.vec_and_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecOrVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_or_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecOrScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {
        a.usePinned { pinA ->
            simd.vec_or_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecXorVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_xor_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecXorScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {
        a.usePinned { pinA ->
            simd.vec_xor_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecNot(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_not(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecLShiftVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_lshift_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecLShiftScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {
        a.usePinned { pinA ->
            simd.vec_lshift_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecRShiftVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_rshift_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecRShiftScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Int) {
        a.usePinned { pinA ->
            simd.vec_rshift_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecEqVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int, rtol: Double, atol: Double, allowNan: Boolean) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_eq_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize, rtol, atol, allowNan)
            }
        }
    }

    actual fun vecEqScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double, rtol: Double, atol: Double, allowNan: Boolean) {
        a.usePinned { pinA ->
            simd.vec_eq_scalar(pinA.addressOf(aOffset), b, aSize, rtol, atol, allowNan)
        }
    }

    actual fun vecNeqVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int, rtol: Double, atol: Double, allowNan: Boolean) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_neq_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize, rtol, atol, allowNan)
            }
        }
    }

    actual fun vecNeqScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double, rtol: Double, atol: Double, allowNan: Boolean) {
        a.usePinned { pinA ->
            simd.vec_neq_scalar(pinA.addressOf(aOffset), b, aSize, rtol, atol, allowNan)
        }
    }

    actual fun vecLtVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_lt_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecLtScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_lt_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecLteVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_lte_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecLteScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_lte_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecGtVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_gt_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecGtScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_gt_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecGteVec(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_gte_vec(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecGteScalar(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_gte_scalar(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecIsNan(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_isnan(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecIsInf(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_isinf(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecIsFinite(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_isfinite(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecSqrt(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_sqrt(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecPow(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_pow(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun veciPow(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_ipow(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecLog(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_log(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecLogBase(a: DoubleArray, aOffset: Int, aSize: Int, b: Double) {
        a.usePinned { pinA ->
            simd.vec_logbase(pinA.addressOf(aOffset), b, aSize)
        }
    }

    actual fun vecExp(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_exp(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecExpm1(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_expm1(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecLog1p(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_log1p(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecLog2(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_log2(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecLog10(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_log10(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecCopy(dest: DoubleArray, destOffset: Int, destSize: Int, src: DoubleArray, srcOffset: Int) {
        dest.usePinned { pinDest ->
            src.usePinned { pinSrc ->
                simd.vec_copy(pinDest.addressOf(destOffset), pinSrc.addressOf(srcOffset), destSize)
            }
        }
    }

    actual fun getSimdSize(): Int {
        return simd.get_simd_size()
    }

    actual fun vecSum(a: DoubleArray, aOffset: Int, aSize: Int): Double {
        a.usePinned { pinA ->
            return simd.vec_sum(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecMin(a: DoubleArray, aOffset: Int, aSize: Int): Double {
        a.usePinned { pinA ->
            return simd.vec_min(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecMax(a: DoubleArray, aOffset: Int, aSize: Int): Double {
        a.usePinned { pinA ->
            return simd.vec_max(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecProduct(a: DoubleArray, aOffset: Int, aSize: Int): Double {
        a.usePinned { pinA ->
            return simd.vec_prod(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecMean(a: DoubleArray, aOffset: Int, aSize: Int): Double {
        a.usePinned { pinA ->
            return simd.vec_mean(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecVariance(a: DoubleArray, aOffset: Int, aSize: Int): Double {
        a.usePinned { pinA ->
            return simd.vec_var(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecStdDev(a: DoubleArray, aOffset: Int, aSize: Int): Double {
        a.usePinned { pinA ->
            return simd.vec_std(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecCoerce(a: DoubleArray, aOffset: Int, aSize: Int, min: Double, max: Double) {
        a.usePinned { pinA ->
            simd.vec_coerce(pinA.addressOf(aOffset), aSize, min, max)
        }
    }

    actual fun vecFloor(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_floor(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecCeil(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_ceil(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecTrunc(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_trunc(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecRound(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_round(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecSin(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_sin(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecCos(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_cos(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecTan(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_tan(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecAsin(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_asin(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecAcos(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_acos(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecAtan(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_atan(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecAtan2(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_atan2(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecSinh(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_sinh(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecCosh(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_cosh(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecTanh(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_tanh(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecAsinh(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_asinh(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecAcosh(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_acosh(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecAtanh(a: DoubleArray, aOffset: Int, aSize: Int) {
        a.usePinned { pinA ->
            simd.vec_atanh(pinA.addressOf(aOffset), aSize)
        }
    }

    actual fun vecHypot(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int) {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                simd.vec_hypot(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecDot(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int): Double {
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                return simd.vec_dot(pinA.addressOf(aOffset), pinB.addressOf(bOffset), aSize)
            }
        }
    }

    actual fun vecMatMul(a: DoubleArray, aOffset: Int, aSize: Int, b: DoubleArray, bOffset: Int, n: Int, m: Int, p: Int): DoubleArray {
        val c = DoubleArray(m * p)
        a.usePinned { pinA ->
            b.usePinned { pinB ->
                c.usePinned { pinC ->
                    simd.vec_matmul(pinA.addressOf(aOffset), pinB.addressOf(bOffset), pinC.addressOf(0), n, m, p)
                }
            }
        }
        return c
    }
}

@file:Suppress("FunctionName")

package com.martmists.ndarray.simd.jni

import jni.*
import kotlinx.cinterop.*
import simd.*

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecEqVec")
fun jni_vec_eq_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_eq_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecEqScalar")
fun jni_vec_eq_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_eq_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecNeqVec")
fun jni_vec_neq_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_neq_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecNeqScalar")
fun jni_vec_neq_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_neq_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecGtVec")
fun jni_vec_gt_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_gt_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecGtScalar")
fun jni_vec_gt_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_gt_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecGteVec")
fun jni_vec_gte_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_gte_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecGteScalar")
fun jni_vec_gte_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_gte_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecLtVec")
fun jni_vec_lt_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_lt_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecLtScalar")
fun jni_vec_lt_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_lt_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecLteVec")
fun jni_vec_lte_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_lte_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecLteScalar")
fun jni_vec_lte_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_lte_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

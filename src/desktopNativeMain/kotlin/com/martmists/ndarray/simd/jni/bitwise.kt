@file:Suppress("FunctionName")

package com.martmists.ndarray.simd.jni

import jni.*
import kotlinx.cinterop.*
import simd.*

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecAndVec")
fun jni_vec_and_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_and_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecAndScalar")
fun jni_vec_and_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_and_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecOrVec")
fun jni_vec_or_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_or_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecOrScalar")
fun jni_vec_or_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_or_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecXorVec")
fun jni_vec_xor_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_xor_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecXorScalar")
fun jni_vec_xor_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_xor_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecNot")
fun jni_vec_not(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_not(refA, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecLShiftVec")
fun jni_vec_lshift_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_lshift_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecLShiftScalar")
fun jni_vec_lshift_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_lshift_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecRShiftVec")
fun jni_vec_rshift_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_rshift_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecRShiftScalar")
fun jni_vec_rshift_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_rshift_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

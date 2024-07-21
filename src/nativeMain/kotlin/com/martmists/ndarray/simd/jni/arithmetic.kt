@file:Suppress("FunctionName")

package com.martmists.ndarray.simd.jni

import jni.*
import kotlinx.cinterop.*
import simd.*

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecAddVec")
fun jni_vec_add_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_add_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical !!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecAddScalar")
fun jni_vec_add_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_add_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecSubVec")
fun jni_vec_sub_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_sub_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecSubScalar")
fun jni_vec_sub_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_sub_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecMulVec")
fun jni_vec_mul_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_mul_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical !!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecMulScalar")
fun jni_vec_mul_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_mul_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecDivVec")
fun jni_vec_div_vec(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        vec_div_vec(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical !!.invoke(env, b, arrB, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecDivScalar")
fun jni_vec_div_scalar(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdouble) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_div_scalar(refA, b, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecNegate")
fun jni_vec_negate(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_negate(refA, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecAbs")
fun jni_vec_abs(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_abs(refA, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

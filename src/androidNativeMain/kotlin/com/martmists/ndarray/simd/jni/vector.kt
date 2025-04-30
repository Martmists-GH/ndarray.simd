@file:Suppress("FunctionName")

package com.martmists.ndarray.simd.jni

import platform.android.*
import kotlinx.cinterop.*
import simd.*

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecDot")
fun jni_vec_dot(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint): jdouble {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())

        val res = vec_dot(refA, refB, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical !!.invoke(env, b, arrB, 0)

        return res
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecMatMul")
fun jni_vec_mat_mul(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint, b: jdoubleArray, bOffset: jint, m: jint, n: jint, p: jint): jdoubleArray? {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())
        val arrB = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, b, null)!!.reinterpret<DoubleVar>()
        val refB = interpretCPointer<DoubleVar>(arrB.rawValue + bOffset * sizeOf<DoubleVar>())
        val c = env.pointed.pointed!!.NewDoubleArray!!.invoke(env, m * p)
        val arrC = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, c, null)!!.reinterpret<DoubleVar>()

        vec_matmul(refA, refB, arrC, m, n, p)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, b, arrB, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, c, arrC, 0)

        return c
    }
}

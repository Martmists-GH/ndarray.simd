@file:Suppress("FunctionName")

package com.martmists.ndarray.simd.jni

import jni.JNIEnvVar
import jni.jdoubleArray
import jni.jint
import jni.jobject
import kotlinx.cinterop.*
import simd.*

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecFloor")
fun jni_vec_floor(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_floor(refA, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecCeil")
fun jni_vec_ceil(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_ceil(refA, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecTrunc")
fun jni_vec_trunc(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_trunc(refA, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecRound")
fun jni_vec_round(env: CPointer<JNIEnvVar>, thisObject: jobject, a: jdoubleArray, aOffset: jint, aSize: jint) {
    memScoped {
        val arrA = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, a, null)!!.reinterpret<DoubleVar>()
        val refA = interpretCPointer<DoubleVar>(arrA.rawValue + aOffset * sizeOf<DoubleVar>())

        vec_round(refA, aSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, a, arrA, 0)
    }
}

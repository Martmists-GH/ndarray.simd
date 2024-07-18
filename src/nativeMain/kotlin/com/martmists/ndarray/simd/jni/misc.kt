package com.martmists.ndarray.simd.jni

import jni.JNIEnvVar
import jni.jdoubleArray
import jni.jint
import jni.jobject
import kotlinx.cinterop.*
import simd.*

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_vecCopy")
fun jni_vec_copy(env: CPointer<JNIEnvVar>, thisObject: jobject, dest: jdoubleArray, destOffset: jint, destSize: jint, src: jdoubleArray, srcOffset: jint) {
    val size = env.pointed.pointed!!.GetArrayLength!!.invoke(env, src)
    memScoped {
        val arrSrc = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, src, null)!!.reinterpret<DoubleVar>()
        val refSrc = interpretCPointer<DoubleVar>(arrSrc.rawValue + srcOffset * sizeOf<DoubleVar>())
        val arrDest = env.pointed.pointed!!.GetPrimitiveArrayCritical!!.invoke(env, dest, null)!!.reinterpret<DoubleVar>()
        val refDest = interpretCPointer<DoubleVar>(arrDest.rawValue + destOffset * sizeOf<DoubleVar>())

        vec_copy(refSrc, refDest, destSize)

        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, src, arrSrc, 0)
        env.pointed.pointed!!.ReleasePrimitiveArrayCritical!!.invoke(env, dest, arrDest, 0)
    }
}

@CName("Java_com_martmists_ndarray_simd_NativeSpeedup_getSimdSize")
fun jni_get_simd_size(env: CPointer<JNIEnvVar>, thisObject: jobject): jint {
    return get_simd_size().convert()
}

#include <jni.h>
#include "com_makeryan_modules_jnis_Mk.h"

/*
 * Class:     com_makeryan_modules_jnis_Mk
 * Method:    getStaticMessage
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_makeryan_modules_jnis_Mk_getStaticMessage
        (JNIEnv *env, jclass) {
    return env->NewStringUTF("静态方法获取到的Message");
}

/*
 * Class:     com_makeryan_modules_jnis_Mk
 * Method:    getMessage
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_makeryan_modules_jnis_Mk_getMessage
        (JNIEnv *env, jobject) {
    return env->NewStringUTF("非静态方法获取到的Message");
}
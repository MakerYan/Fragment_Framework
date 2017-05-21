#include <jni.h>
#include "com_makeryan_modules_MyNdk.h"


/*
 * Class:     com_makeryan_modules_MyNdk
 * Method:    getMessage
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_makeryan_modules_MyNdk_getStaticMessage
        (JNIEnv *env, jclass) {
    return env->NewStringUTF("静态方法获取到的Message");
}

/*
 * Class:     com_makeryan_modules_MyNdk
 * Method:    getMessage
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_makeryan_modules_MyNdk_getMessage
        (JNIEnv *env, jobject) {
    return env->NewStringUTF("非静态方法获取到的Message");
}
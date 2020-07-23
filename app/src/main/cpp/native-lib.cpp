//
// Created by tony on 2020/7/22.
//

#include <jni.h>
#include <string>
#include "logger.h"

extern "C"
JNIEXPORT jstring  JNICALL
Java_cn_salesuite_saf_kotlin_utils_JNIUtils_stringFromJNI(JNIEnv *env, jclass clazz) {
    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}
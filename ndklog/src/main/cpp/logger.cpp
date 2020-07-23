//
// Created by tony on 2020-01-03.
//

#include "utility.h"

typedef enum {
    ERROR,
    WARN,
    INFO,
    DEBUG
}LogLevel;

static const char *FilePrinterClassName = "com/safframework/log/printer/FilePrinter";

JavaVM *javaVM = NULL;
jobject filePrinter_ref = NULL;

void onLoad(JavaVM *vm,jobject jFilePrinter) {
    javaVM = vm;
    filePrinter_ref = jFilePrinter;
}

JNIEnv *getJNIEnv() {
    JNIEnv *env = NULL;
    if (javaVM == NULL || javaVM->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return NULL;
    }
    return env;
}

void onUnload() {
    JNIEnv *env = getJNIEnv();
    env->DeleteGlobalRef(filePrinter_ref);  // 删除全局引用
}

void Log(LogLevel logLevel, const char *tag, const char *msg) {
    JNIEnv *env = getJNIEnv();
    if (env == NULL) {
        return;
    }
    jclass clazz = env->FindClass(FilePrinterClassName);
    if (clazz == NULL) {
        utility::logError("can't find jclass");
        return;
    }

    jclass clazzLogLevel = env->FindClass("com/safframework/log/LogLevel");
    jobject objLogLevel;
    jfieldID j_fieldID_ERROR = env->GetStaticFieldID(clazzLogLevel,"ERROR","Lcom/safframework/log/LogLevel;");
    jfieldID j_fieldID_WARN  = env->GetStaticFieldID(clazzLogLevel,"WARN","Lcom/safframework/log/LogLevel;");
    jfieldID j_fieldID_INFO  = env->GetStaticFieldID(clazzLogLevel,"INFO","Lcom/safframework/log/LogLevel;");
    jfieldID j_fieldID_DEBUG = env->GetStaticFieldID(clazzLogLevel,"DEBUG","Lcom/safframework/log/LogLevel;");

    switch (logLevel){
        case ERROR:
            objLogLevel = env->GetStaticObjectField(clazzLogLevel,j_fieldID_ERROR);
            break;
        case WARN:
            objLogLevel = env->GetStaticObjectField(clazzLogLevel,j_fieldID_WARN);
            break;
        case INFO:
            objLogLevel = env->GetStaticObjectField(clazzLogLevel,j_fieldID_INFO);
            break;
        case DEBUG:
            objLogLevel = env->GetStaticObjectField(clazzLogLevel,j_fieldID_DEBUG);
            break;
        default:
            objLogLevel = nullptr;
    }
    env->DeleteLocalRef(clazzLogLevel);

    if (env->IsInstanceOf(filePrinter_ref, clazz)) { // 判断 filePrinter_ref 是否为 FilePrinter 类
        jmethodID methodId = env->GetMethodID(clazz, "printLog","(Lcom/safframework/log/LogLevel;Ljava/lang/String;Ljava/lang/String;)V");
        env->CallVoidMethod(filePrinter_ref, methodId, objLogLevel,env->NewStringUTF(tag), env->NewStringUTF(msg));
    }
}

void LogD(const char *tag, const char *msg) {
    utility::logDebug(tag,msg);
    Log(DEBUG, tag, msg);
}

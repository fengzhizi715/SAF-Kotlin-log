//
// Created by Tony on 2020-01-03.
//

#ifndef SAF_KOTLIN_LOG_UTILITY_H
#define SAF_KOTLIN_LOG_UTILITY_H

#include <jni.h>
#include <android/log.h>

#define  LOG_TAG "NDK_LOG"

#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

namespace utility
{
    void logError(const char *message) {
        LOGE("%s", message);
    }

    void logError(const char *tag, const char *message) {
        __android_log_print(ANDROID_LOG_ERROR,tag,"%s", message);
    }

    void logWarn(const char *message) {
        LOGW("%s", message);
    }

    void logWarn(const char *tag, const char *message) {
        __android_log_print(ANDROID_LOG_WARN,tag,"%s", message);
    }

    void logDebug(const char *message) {
        LOGD("%s", message);
    }

    void logDebug(const char *tag, const char *message) {
        __android_log_print(ANDROID_LOG_DEBUG,tag,"%s", message);
    }

    void logInfo(const char *message) {
        LOGI("%s", message);
    }

    void logInfo(const char *tag, const char *message) {
        __android_log_print(ANDROID_LOG_INFO,tag,"%s", message);
    }
}

#endif //SAF_KOTLIN_LOG_UTILITY_H

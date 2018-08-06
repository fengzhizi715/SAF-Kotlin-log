//
// Created by Tony on 2018/8/6.
//
#ifndef SAF_KOTLIN_LOG_LOG_H
#define SAF_KOTLIN_LOG_LOG_H

#ifdef __cplusplus
extern "C" {
#endif

#include <android/log.h>

#define LOG_TAG "mmap"
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__))
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__))

#ifdef __cplusplus
}
#endif

#endif //SAF_KOTLIN_LOG_LOG_H

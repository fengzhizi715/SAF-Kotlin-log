//
// Created by chan on 2018/1/28.
//

#ifndef SAF_KOTLIN_LOG_SCOPEBYTEARRAY_H
#define SAF_KOTLIN_LOG_SCOPEBYTEARRAY_H

#include <jni.h>

class ScopeByteArray {
private:
    JNIEnv *mEnv;
    const jbyteArray mArray;
    jbyte *mBytes;
public:
    ScopeByteArray(JNIEnv *mEnv, const jbyteArray array) : mEnv(mEnv), mArray(array) {
        mBytes = mEnv->GetByteArrayElements(mArray, NULL);
    }

    ~ScopeByteArray() {
        mEnv->ReleaseByteArrayElements(mArray, mBytes, 0);
    }

    jbyte *getBytes() {
        return mBytes;
    }
};

#endif //SAF_KOTLIN_LOG_SCOPEBYTEARRAY_H

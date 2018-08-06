//
// Created by chan on 2018/1/22.
//

#ifndef SAF_KOTLIN_LOG_SCOPESTRING_H
#define SAF_KOTLIN_LOG_SCOPESTRING_H

#ifdef __cplusplus
extern "C" {
#endif

#include <jni.h>

class ScopeString {
private:
    JNIEnv *mEnv;
    const jstring mJString;
    const char *mCString;

public:
    ScopeString(JNIEnv *env, const jstring jstr) : mEnv(env), mJString(jstr) {
        mCString = mEnv->GetStringUTFChars(mJString, 0);
    }

    virtual ~ScopeString() {
        mEnv->ReleaseStringUTFChars(mJString, mCString);
    }

    const char *getCString() const {
        return mCString;
    }
};

#ifdef __cplusplus
}
#endif

#endif //SAF_KOTLIN_LOG_SCOPESTRING_H

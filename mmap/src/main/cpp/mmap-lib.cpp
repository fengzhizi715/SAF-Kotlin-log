//
// Created by Tony on 2018/8/6.
//

#include <string>
#include <sstream>
#include <unistd.h>
#include "mmap.h"

#include "ScopeString.h"
#include "ScopeByteArray.h"

#include <sys/mman.h>

mmap_info *get_mmap_info(JNIEnv *env, jobject object) {
    jclass mmap_record_clazz = env->GetObjectClass(object);
    jfieldID reference_id = env->GetFieldID(mmap_record_clazz, "mBufferInfoReference", "J");
    return reinterpret_cast<mmap_info *>(env->GetLongField(object, reference_id));
}

void set_mmap_info(JNIEnv *env, jobject object, void *ref) {
    jclass mmap_record_clazz = env->GetObjectClass(object);
    jfieldID reference_id = env->GetFieldID(mmap_record_clazz, "mBufferInfoReference", "J");
    env->SetLongField(object, reference_id, (jlong) ref);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_safframework_log_mmap_MmapRecord_init(JNIEnv *env, jobject instance, jstring buffer, jstring log) {
    // open buffer
    ScopeString buffer_path_string_holder(env, buffer);
    const char *buffer_path = buffer_path_string_holder.getCString();

    // open log
    ScopeString log_path_string_holder(env, log);
    const char *log_path = log_path_string_holder.getCString();

    mmap_info *info = new mmap_info;
    open_buffer(buffer_path, log_path, info);
    set_mmap_info(env, instance, info);
    return 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_safframework_log_mmap_MmapRecord_release(JNIEnv *env, jobject instance) {
    mmap_info *info = get_mmap_info(env, instance);
    if (info == nullptr || info->buffer == nullptr) {
        return;
    }

    memset(info->buffer, 0, info->buffer_size);
    munmap(info->buffer, info->buffer_size);
    close(info->buffer_fd);
    close(info->path_fd);
}

JNIEXPORT void JNICALL
Java_com_safframework_log_mmap_MmapRecord_save(JNIEnv *env, jobject object, jbyteArray bytes) {
    mmap_info *info = get_mmap_info(env, object);
    if (info == nullptr) {
        return;
    }

    jsize len = env->GetArrayLength(bytes);
    ScopeByteArray scopeByteArray(env, bytes);
    jbyte *data = scopeByteArray.getBytes();

    write_buffer(info, (const u1 *) data, (size_t) len);
}

extern "C"
JNIEXPORT jbyteArray JNICALL
Java_com_safframework_log_mmap_MmapRecord_read(JNIEnv *env, jobject instance) {
    mmap_info *info = get_mmap_info(env, instance);
    if (info == nullptr || info->buffer_size <= 0 || info->buffer == nullptr) {
        return nullptr;
    }

    buffer_header header;
    int result = check_header(info->buffer, info->buffer_size, &header);
    if (result != 0) {
        return nullptr;
    }

    jbyteArray array = env->NewByteArray(header.size);
    env->SetByteArrayRegion(array, 0, header.size,
                            (const jbyte *) (info->buffer + sizeof(buffer_header)));
    return array;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_safframework_log_mmap_MmapRecord_recycle(JNIEnv *env, jobject instance, jbyteArray data) {
    if (data != nullptr) {
        env->DeleteLocalRef(data);
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_safframework_log_mmap_MmapRecord_flush(JNIEnv *env, jobject instance) {
    mmap_info *info = get_mmap_info(env, instance);
    if (info == nullptr || info->buffer == nullptr) {
        return;
    }

    buffer_header header;
    int result = check_header(info->buffer, info->buffer_size, &header);
    if (result != 0) {
        return;
    }

    u1 *data = info->buffer + sizeof(header);
    lseek(info->path_fd, 0, SEEK_SET);
    write(info->path_fd, data, header.size);
    fsync(info->path_fd);
    header.size = 0;

    // rewrite header
    memcpy(info->buffer, &header, sizeof(header));
}
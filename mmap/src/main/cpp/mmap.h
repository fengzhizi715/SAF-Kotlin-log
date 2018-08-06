//
// Created by chan on 2018/1/24.
//

#ifndef SAF_KOTLIN_LOG_MMAP_H
#define SAF_KOTLIN_LOG_MMAP_H

#include <cwchar>
#include <sys/mman.h>

#ifdef __cplusplus
extern "C" {
#endif

#ifdef NEED_FREE
#undef NEED_FREE
#define NEED_FREE
#else
#define NEED_FREE
#endif

typedef unsigned char u1;
typedef unsigned short u2;
typedef unsigned int u4;

const u1 MAGIC_HEADER[] = {0x05, 0x21, 0x05, 0x25, 0x12, 0x12, 0x01, 0x18};

#define TYPE_DATA 0x01;
#define HEADER_V1 0x01;

typedef struct {
    u1 magic[sizeof(MAGIC_HEADER)];
    u4 size = 0;
    u2 type = TYPE_DATA;
    u2 version = HEADER_V1;
} buffer_header;


class mem_info {
public:
    u1 *buffer;
    buffer_header *header;
    u4 size;

    mem_info() {
        buffer = nullptr;
        header = new buffer_header;
        size = 0;
    }

    ~mem_info() {
        if (header != nullptr) {
            delete header;
        }

        if (buffer != nullptr) {
            munmap(buffer, size);
        }
    }
};

typedef struct {
    int buffer_fd;
    int path_fd;
    u4 buffer_size;
    u1 *buffer;
} mmap_info;


#define ERROR_INVALID_ARGUMENT -1
#define ERROR_OPEN_BUFFER -2
#define ERROR_OPEN_PATH -3
#define ERROR_ALLOC_MMAP -4

#define RESIZE(a) (((a) / PAGE_SIZE + 1) * PAGE_SIZE)


int open_buffer(const char *buffer_path, const char *path, mmap_info *info);

void write_buffer(mmap_info *info, const u1 *data, size_t data_size);

int check_header(const u1 *buffer, size_t size, buffer_header *header);

#ifdef __cplusplus
}
#endif

#endif //SAF_KOTLIN_LOG_MMAP_H

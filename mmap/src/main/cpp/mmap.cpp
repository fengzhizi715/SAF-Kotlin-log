//
// Created by chan on 2018/1/24.
//

#include <sys/mman.h>
#include <sys/stat.h>
#include "mmap.h"
#include "log.h"
#include <fcntl.h>
#include <unistd.h>

#ifdef __cplusplus
extern "C" {
#endif

#include <string.h>

void read_dirty_data(int fd, mem_info *info);

u1 *mmap_alloc(int fd, size_t size);

int check_header(const u1 *buffer, size_t size, buffer_header *header) {
    if (size < sizeof(buffer_header)) {
        return -1;
    }

    if (buffer == nullptr) {
        return -2;
    }

    int result = memcmp(buffer, MAGIC_HEADER, sizeof(MAGIC_HEADER));
    if (result == 0) {
        memcpy(header, buffer, sizeof(buffer_header));
    }
    return result;
}

int open_buffer(const char *buffer_path, const char *path, mmap_info *info) {
    if (strlen(buffer_path) == 0 || strlen(path) == 0 || info == nullptr) {
        LOGD("open_buffer, invalid arguments");
        return ERROR_INVALID_ARGUMENT;
    }

    int buffer_fd = ::open(buffer_path, O_RDWR | O_CREAT, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
    if (buffer_fd < 0) {
        LOGD("open buffer failed");
        return ERROR_OPEN_BUFFER;
    }

    int path_fd = ::open(path, O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
    if (path_fd < 0) {
        LOGD("open dest path failed");
        close(buffer_fd);
        return ERROR_OPEN_PATH;
    }

    mem_info mem_info;
    read_dirty_data(buffer_fd, &mem_info);
    if (mem_info.buffer != nullptr && mem_info.size != 0) {
        // write data to path
        LOGD("write dirty data. size %d", mem_info.header->size);
        lseek(path_fd, 0, SEEK_SET);
        write(path_fd, mem_info.buffer + sizeof(buffer_header), mem_info.header->size);
        fsync(path_fd);
    }

    info->buffer_size = RESIZE(128);
    info->buffer_fd = buffer_fd;
    info->path_fd = path_fd;
    info->buffer = mmap_alloc(buffer_fd, info->buffer_size);
    if (info->buffer == nullptr) {
        LOGD("mmap failed");
        return ERROR_ALLOC_MMAP;
    }

    buffer_header header;
    header.size = 0;
    memcpy(header.magic, MAGIC_HEADER, sizeof(MAGIC_HEADER));
    memcpy(info->buffer, &header, sizeof(buffer_header));

    return 0;
}

u1 *mmap_alloc(int fd, size_t size) {
    ftruncate(fd, (off_t) size);
    lseek(fd, 0, SEEK_SET);
    size_t alloc_size = size;
    u1 *map_ptr = (u1 *) mmap(0, alloc_size, PROT_WRITE | PROT_READ, MAP_SHARED, fd, 0);
    return map_ptr == MAP_FAILED ? nullptr : map_ptr;
}

void read_dirty_data(int fd, mem_info *info) {
    info->buffer = nullptr;
    info->size = 0;

    // read dirty data
    struct stat buffer_file_stat;
    if (fstat(fd, &buffer_file_stat) < 0) {
        LOGD("read_dirty_data, has no dirty data");
        return;
    }

    size_t buffer_file_size = (size_t) buffer_file_stat.st_size;
    if (buffer_file_size < sizeof(buffer_header)) {
        LOGD("find dirty data, but size if less than buffer_header");
        return;
    }

    u1 *buffer = (u1 *) mmap(0, buffer_file_size, PROT_WRITE | PROT_READ, MAP_SHARED, fd, 0);
    if (buffer == MAP_FAILED) {
        LOGD("read dirty data");
        return;
    }

    int result = check_header(buffer, buffer_file_size, info->header);
    if (result != 0) {
        LOGD("invalid dirty data");
        munmap(buffer, buffer_file_size);
        return;
    }

    info->buffer = buffer;
    info->size = buffer_file_size;
}

void write_buffer(mmap_info *info, const u1 *data, size_t data_size) {
    // need to allocate more buffer
    if (info->buffer_size <= data_size + sizeof(buffer_header)) {
        u4 resize_size = RESIZE(info->buffer_size);
        ftruncate(info->buffer_fd, (off_t) resize_size);
//        LOGD("size %d, address %x", info->buffer_size, (u4) info->buffer);
        info->buffer = (u1 *) mremap(info->buffer, info->buffer_size, resize_size, MREMAP_MAYMOVE);
        info->buffer_size = resize_size;
//        LOGD("resize buffer %d, address %x", info->buffer_size, (u4) info->buffer);

        if (info->buffer == MAP_FAILED) {
            LOGD("remap failed");
            info->buffer = nullptr;
        }
    }

    if (info->buffer == nullptr) {
        LOGD("ignore write buffer");
        return;
    }

    buffer_header header;
    int result = check_header(info->buffer, info->buffer_size, &header);
    if (result != 0) {
        LOGD("write_buffer, invalid header");
        return;
    }

    header.size = data_size;
    LOGD("header size: %d", header.size);
    memcpy(info->buffer, &header, sizeof(buffer_header));
    memcpy(info->buffer + sizeof(buffer_header), data, data_size);
}

#ifdef __cplusplus
}
#endif
package com.safframework.log.mmap;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.safframework.log.mmap.MmapRecord.java
 * @author: Tony Shen
 * @date: 2018-08-08 11:34
 */
public class MmapRecord {

    private long mBufferInfoReference;

    static {
        System.loadLibrary("mmap");
    }

    public MmapRecord(String bufferPath, String path) {
        init(bufferPath, path);
    }

    private native int init(String bufferPath, String path);

    public native void release();

    public native void save(byte[] data);

    public native byte[] read();

    public native void recycle(byte[] data);

    public native void flush();
}

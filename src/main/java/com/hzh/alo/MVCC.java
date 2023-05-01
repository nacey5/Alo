package com.hzh.alo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MVCC {

    private Map<String, Map<Long, String>> database;
    private ReentrantReadWriteLock lock;

    public MVCC() {
        database = new HashMap<>();
        lock = new ReentrantReadWriteLock();
    }

    // 读取数据
    public String read(String key, long timestamp) {
        lock.readLock().lock();
        try {
            Map<Long, String> versions = database.get(key);
            if (versions == null) {
                return null;
            }
            for (long i = timestamp; i >= 0; i--) {
                String value = versions.get(i);
                if (value != null) {
                    return value;
                }
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    // 写入数据
    public void write(String key, String value) {
        lock.writeLock().lock();
        try {
            Map<Long, String> versions = database.get(key);
            if (versions == null) {
                versions = new HashMap<>();
                database.put(key, versions);
            }
            long timestamp = System.currentTimeMillis();
            versions.put(timestamp, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

}

package com.foxcr.ycdevcore.integration.cache

import android.util.LruCache

class LruCacheProxy<K, V>(initialMaxSize:Int): Cache<K, V> {
    private val lruCache: LruCache<K, V> = LruCache(initialMaxSize)

    @Synchronized
    override fun size(): Int {
        return lruCache.size()
    }

    @Synchronized
    override fun getMaxSize(): Int {
        return lruCache.maxSize()
    }

    @Synchronized
    override fun get(key: K): V? {
        return lruCache.get(key)
    }

    @Synchronized
    override fun put(key: K, value: V): V? {
        return lruCache.put(key,value)
    }

    @Synchronized
    override fun remove(key: K): V? {
        return lruCache.remove(key)
    }

    @Synchronized
    override fun clear() {
        lruCache.evictAll()
    }
}
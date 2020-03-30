package com.foxcr.ycdevcore.integration.cache

class IntelligentCache<K,V>(size:Int): Cache<K, V> {
    private val mMap: MutableMap<K, V>//可将数据永久存储至内存中的存储容器
    private val mCache: Cache<K, V>//当达到最大容量时可根据 LRU 算法抛弃不合规数据的存储容器


    init {
        this.mMap = HashMap()
        this.mCache = LruCacheProxy(size)
    }

    @Synchronized
    override fun size(): Int {
        return mMap.size + mCache.size()
    }

    @Synchronized
    override fun getMaxSize(): Int {
        return mMap.size + mCache.getMaxSize()
    }

    @Synchronized
    override fun get(key: K): V? {
        return if(key is String){
            if (key.startsWith(KEY_KEEP)) {
                mMap[key]
            } else mCache[key]
        } else mCache[key]
    }

    @Synchronized
    override fun put(key: K, value: V): V? {
        return if(key is String){
            if (key.startsWith(KEY_KEEP)) {
                mMap.put(key, value)
            } else mCache.put(key, value)
        }else mCache.put(key, value)
    }

    @Synchronized
    override fun remove(key: K): V? {
        return if(key is String){
            if (key.startsWith(KEY_KEEP)) {
                mMap.remove(key)
            } else mCache.remove(key)
        }else mCache.remove(key)
    }

    @Synchronized
    override fun clear() {
        mCache.clear()
        mMap.clear()
    }

    companion object {
        const val KEY_KEEP = "Keep="
        fun getKeyOfKeep(key: String): String {
            return KEY_KEEP + key
        }
    }
}
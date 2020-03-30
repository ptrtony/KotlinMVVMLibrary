package com.foxcr.ycdevcore.integration.cache

import android.app.Application

class CacheFactory(val application: Application): Cache.Factory {
    override fun <K,V> build(type: CacheType): Cache<K, V> {
        //若想自定义 LruCache 的 size, 或者不想使用 LruCache, 想使用自己自定义的策略
        //使用 GlobalConfigModule.Builder#cacheFactory() 即可扩展
        return when (type.getCacheTypeId()) {
            CacheType.EXTRAS_TYPE_ID -> IntelligentCache(type.calculateCacheSize(application))
            else -> LruCacheProxy(type.calculateCacheSize(application))
        }
    }
}
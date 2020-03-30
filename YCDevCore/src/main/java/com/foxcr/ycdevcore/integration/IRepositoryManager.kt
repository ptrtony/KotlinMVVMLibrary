package com.foxcr.ycdevcore.integration



/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 */
interface IRepositoryManager {

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
    </T> */
    fun <T> obtainRetrofitService(service: Class<T>): T

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
    </T> */
    fun <T> obtainCacheService(cache: Class<T>): T

    /**
     * 清理所有缓存
     */
    fun clearAllCache()

    fun <T> addRetrofitServiceProxy(service: Class<T>,serviceImpl:T)
}

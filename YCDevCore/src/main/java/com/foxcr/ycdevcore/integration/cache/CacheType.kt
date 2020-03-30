package com.foxcr.ycdevcore.integration.cache

import android.app.ActivityManager
import android.content.Context

interface CacheType {

    /**
     * 返回框架内需要缓存的模块对应的 `id`
     *
     * @return
     */
    fun getCacheTypeId(): Int

    /**
     * 计算对应模块需要的缓存大小
     *
     * @return
     */
    fun calculateCacheSize(context: Context): Int


    companion object {
        const val RETROFIT_SERVICE_CACHE_TYPE_ID = 0
        const val CACHE_SERVICE_CACHE_TYPE_ID = 1
        const val EXTRAS_TYPE_ID = 2
        const val ACTIVITY_CACHE_TYPE_ID = 3
        const val FRAGMENT_CACHE_TYPE_ID = 4

        val RETROFIT_SERVICE_CACHE: CacheType = object : CacheType {
            private val MAX_SIZE = 150
            private val MAX_SIZE_MULTIPLIER = 0.002f

            override fun getCacheTypeId(): Int {
                return RETROFIT_SERVICE_CACHE_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val targetMemoryCacheSize = (activityManager.memoryClass.toFloat() * MAX_SIZE_MULTIPLIER * 1024f).toInt()
                return if (targetMemoryCacheSize >= MAX_SIZE) {
                    MAX_SIZE
                } else targetMemoryCacheSize
            }
        }

        val CACHE_SERVICE_CACHE: CacheType = object : CacheType {
            private val MAX_SIZE = 150
            private val MAX_SIZE_MULTIPLIER = 0.002f

            override fun getCacheTypeId(): Int {
                return CACHE_SERVICE_CACHE_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val targetMemoryCacheSize = (activityManager.memoryClass.toFloat() * MAX_SIZE_MULTIPLIER * 1024f).toInt()
                return if (targetMemoryCacheSize >= MAX_SIZE) {
                    MAX_SIZE
                } else targetMemoryCacheSize
            }
        }

        val EXTRAS: CacheType = object : CacheType {
            private val MAX_SIZE = 500
            private val MAX_SIZE_MULTIPLIER = 0.005f

            override fun getCacheTypeId(): Int {
                return EXTRAS_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val targetMemoryCacheSize = (activityManager.memoryClass.toFloat() * MAX_SIZE_MULTIPLIER * 1024f).toInt()
                return if (targetMemoryCacheSize >= MAX_SIZE) {
                    MAX_SIZE
                } else targetMemoryCacheSize
            }
        }
    }


}
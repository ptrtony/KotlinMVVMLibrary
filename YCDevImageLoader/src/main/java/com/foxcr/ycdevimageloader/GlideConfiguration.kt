package com.foxcr.ycdevimageloader

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.foxcr.ycdevcore.integration.imageloader.ImageLoader
import com.foxcr.ycdevcore.utils.makeDirs
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import okhttp3.OkHttpClient
import org.kodein.di.generic.instance
import java.io.File
import java.io.InputStream

@GlideModule(glideName = "GlideQuick")
class GlideConfiguration: AppGlideModule() {
    private var IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024//图片缓存文件最大值为100Mb

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val cacheFile:File by obtainAppKodeinAware().instance(tag = "cacheFile")
        builder.setDiskCache { DiskLruCacheWrapper.create(makeDirs(File(cacheFile, "Glide")), IMAGE_DISK_CACHE_MAX_SIZE.toLong()) }

        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize

        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()

        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))

        val imageLoader: ImageLoader by obtainAppKodeinAware().instance()
        val imageLoaderStrategy = imageLoader.mStrategy
        if(imageLoaderStrategy is GlideAppliesOptions){
            (imageLoaderStrategy as GlideAppliesOptions).applyGlideOptions(context,builder)
        }
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okHttpClient :OkHttpClient by obtainAppKodeinAware().instance()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }

    override fun isManifestParsingEnabled() = false
}
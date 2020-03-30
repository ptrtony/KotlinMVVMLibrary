package com.foxcr.ycdevcore.integration.imageloader

import android.content.Context
import android.view.View
import android.widget.ImageView

class ImageLoader(var mStrategy: ImageLoaderStrategy<ImageConfig>?, private val imageLoaderInterceptors:List<ImageLoaderInterceptor<ImageConfig>>) {
    fun <T : ImageConfig> loadImage(view: View, config: T) {
        val interceptors = ArrayList<ImageLoaderInterceptor<ImageConfig>>()
        interceptors.addAll(imageLoaderInterceptors)
        interceptors.add(ImageLoaderReallyInterceptor<T>() as ImageLoaderInterceptor<ImageConfig>)
        this.mStrategy?.let {
            val reallyInterceptorChain = ImageLoaderReallyInterceptorChain<T>(it,interceptors)
            reallyInterceptorChain.proceed(view,config)
        }
    }

    fun <T : ImageConfig> loadImage(context: Context, config: T) {
        val interceptors = ArrayList<ImageLoaderInterceptor<ImageConfig>>()
        interceptors.addAll(imageLoaderInterceptors)
        interceptors.add(ImageLoaderReallyInterceptor<T>() as ImageLoaderInterceptor<ImageConfig>)
        this.mStrategy?.let {
            val reallyInterceptorChain = ImageLoaderReallyInterceptorChain<T>(it,interceptors)
            reallyInterceptorChain.proceed(context,config)
        }
    }

    fun <T : ImageConfig> clear(imageView: ImageView, config: T) {
        this.mStrategy?.clear(imageView, config)
    }

    fun <T : ImageConfig> clear(view: View, config: T? = null) {
        this.mStrategy?.clear(view, config)
    }

    fun setLoadImgStrategy(strategy: ImageLoaderStrategy<ImageConfig>?) {
        this.mStrategy = strategy
    }

    fun getLoadImgStrategy(): ImageLoaderStrategy<ImageConfig>? {
        return mStrategy
    }
}
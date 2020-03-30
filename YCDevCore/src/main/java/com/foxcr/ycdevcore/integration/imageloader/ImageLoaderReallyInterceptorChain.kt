package com.foxcr.ycdevcore.integration.imageloader

import android.content.Context
import android.view.View

class ImageLoaderReallyInterceptorChain<in T: ImageConfig>(var mStrategy: ImageLoaderStrategy<ImageConfig>
                                                           , var interceptors: List<ImageLoaderInterceptor<ImageConfig>>
                                                           , var index: Int = 0):ImageLoaderInterceptor.Chain<T>{

    override fun proceed(view: View, config: T) {
        if (index >= interceptors.size) throw AssertionError()
        val reallyInterceptorChain = ImageLoaderReallyInterceptorChain<T>(mStrategy,interceptors,index+1) as ImageLoaderInterceptor.Chain<ImageConfig>
        val interceptor = interceptors[index]
        interceptor.intercept(reallyInterceptorChain,view,config)
    }

    override fun proceed(context: Context, config: T) {
        if (index >= interceptors.size) throw AssertionError()
        val reallyInterceptorChain = ImageLoaderReallyInterceptorChain<T>(mStrategy,interceptors,index+1) as ImageLoaderInterceptor.Chain<ImageConfig>
        val interceptor = interceptors[index]
        interceptor.intercept(reallyInterceptorChain,context,config)
    }

    override fun loadImage(view: View, config: T) {
        mStrategy.loadImage(view,config)
    }

    override fun loadImage(context: Context, config: T) {
        mStrategy.loadImage(context,config)
    }
}
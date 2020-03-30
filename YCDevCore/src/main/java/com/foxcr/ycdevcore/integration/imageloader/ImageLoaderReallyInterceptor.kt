package com.foxcr.ycdevcore.integration.imageloader

import android.content.Context
import android.view.View

class ImageLoaderReallyInterceptor<T: ImageConfig>:ImageLoaderInterceptor<T>{
    override fun intercept(chain: ImageLoaderInterceptor.Chain<T>, context: Context, config: T) {
        chain.loadImage(context,config)
    }

    override fun intercept(chain: ImageLoaderInterceptor.Chain<T>, view: View, config: T) {
        chain.loadImage(view,config)
    }
}
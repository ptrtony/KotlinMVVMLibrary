package com.foxcr.ycdevcore.integration.imageloader

import android.content.Context
import android.view.View

interface ImageLoaderInterceptor<K: ImageConfig>{

    fun intercept(chain:Chain<K>,view: View, config: K)
    fun intercept(chain:Chain<K>,context: Context, config: K)

    interface Chain<in T: ImageConfig>{
        fun proceed(view: View, config: T)
        fun proceed(context: Context, config: T)
        fun loadImage(view: View, config: T)
        fun loadImage(context: Context, config: T)
    }
}
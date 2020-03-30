package com.foxcr.ycdevcore.integration.imageloader

import android.content.Context
import android.view.View

interface ImageLoaderStrategy<T: ImageConfig> {
    fun loadImage(view: View, config: T)
    fun loadImage(context: Context, config: T)
    fun clear(view: View, config: T?)
}
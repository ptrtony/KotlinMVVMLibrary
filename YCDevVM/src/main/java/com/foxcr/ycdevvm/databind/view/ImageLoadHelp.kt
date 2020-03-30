package com.foxcr.ycdevvm.databind.view

import android.view.View
import com.foxcr.ycdevcore.integration.imageloader.ImageConfig
import com.foxcr.ycdevcore.integration.imageloader.ImageLoader
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import org.kodein.di.generic.instance

internal object ImageLoadHelp {
    fun loadImage(view: View, imageConfig: ImageConfig){
        val imageLoader: ImageLoader by obtainAppKodeinAware().instance()
        imageLoader.loadImage(view,imageConfig)
    }
}
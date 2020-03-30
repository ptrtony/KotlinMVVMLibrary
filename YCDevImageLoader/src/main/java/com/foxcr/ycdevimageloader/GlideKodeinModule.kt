package com.foxcr.ycdevimageloader


import com.foxcr.ycdevcore.integration.imageloader.ImageConfig
import com.foxcr.ycdevcore.integration.imageloader.ImageLoaderStrategy
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

private const val MODEL_MODULE_TAG = "glideKodeinModule"

val glideKodeinModule = Kodein.Module(MODEL_MODULE_TAG) {

    bind<ImageLoaderStrategy<ImageConfig>>(tag = "quickImageLoader") with provider {
        GlideImageLoaderStrategy() as ImageLoaderStrategy<ImageConfig>
    }

}
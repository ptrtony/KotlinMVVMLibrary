package com.foxcr.ycdevcore.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.foxcr.ycdevcore.integration.IRepositoryManager
import com.foxcr.ycdevcore.integration.RepositoryManager
import com.foxcr.ycdevcore.integration.cache.Cache
import com.foxcr.ycdevcore.integration.cache.CacheType
import com.foxcr.ycdevcore.integration.imageloader.ImageConfig
import com.foxcr.ycdevcore.integration.imageloader.ImageLoader
import com.foxcr.ycdevcore.integration.imageloader.ImageLoaderStrategy
import com.foxcr.ycdevcore.integration.rxsubscriber.RxProgressObservable
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import java.io.File

class AppModule {

    companion object {
        private const val APP_MODULE_TAG = "appModule"
        val appModule by lazy {
            Kodein.Module(APP_MODULE_TAG) {
                bind<GsonBuilder>() with provider { GsonBuilder() }

                bind<IRepositoryManager>() with singleton {
                    RepositoryManager(instance(),instance())
                }

                bind<Gson>() with singleton {
                    instance<GsonBuilder>()
                            .apply {
                                instance<GlobalConfigModule>().provideGsonConfigurations()?.run {
                                    forEach {
                                        it.configGson(instance<Application>(),this@apply)
                                    }
                                }
                            }.create()
                }


                bind<ImageLoader>() with singleton ImageLoader@{
                    return@ImageLoader if(instance<GlobalConfigModule>().provideImageLoaderStrategy()==null){
                        ImageLoader(instanceOrNull<ImageLoaderStrategy<ImageConfig>>(tag = "quickImageLoader")
                                ,instance<GlobalConfigModule>().provideImageLoaderInterceptors())
                    }else{
                        ImageLoader(instance<GlobalConfigModule>().provideImageLoaderStrategy()
                                ,instance<GlobalConfigModule>().provideImageLoaderInterceptors())
                    }
                }

                bind<File>(tag = "cacheFile") with singleton {
                    instance<GlobalConfigModule>().provideCacheFile(instance())
                }

                bind<Cache.Factory>() with singleton {
                    instance<GlobalConfigModule>().provideCacheFactory(instance())
                }

                bind<Cache<String, Any>>() with singleton {
                    instance<Cache.Factory>().build<String, Any>(CacheType.EXTRAS)
                }
            }
        }
    }

    interface GsonConfiguration{
        fun configGson(context: Context, gsonBuilder: GsonBuilder)
    }

    interface RxProgressConfiguration{
        fun provideRxProgressObservable(msg:String,cancelable:Boolean): RxProgressObservable
    }

}
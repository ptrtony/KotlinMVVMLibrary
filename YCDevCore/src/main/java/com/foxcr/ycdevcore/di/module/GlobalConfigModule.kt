package com.foxcr.ycdevcore.di.module

import android.app.Application
import android.text.TextUtils
import com.foxcr.ycdevcore.integration.http.GlobalHttpHandler
import com.foxcr.ycdevcore.integration.cache.Cache
import com.foxcr.ycdevcore.integration.cache.CacheFactory
import com.foxcr.ycdevcore.integration.http.BaseUrl
import com.foxcr.ycdevcore.integration.imageloader.ImageConfig
import com.foxcr.ycdevcore.integration.imageloader.ImageLoaderInterceptor
import com.foxcr.ycdevcore.integration.imageloader.ImageLoaderStrategy
import com.foxcr.ycdevcore.integration.rxsubscriber.RxProgressObservable
import com.foxcr.ycdevcore.integration.rxsubscriber.RxProgressObservableImpl
import com.foxcr.ycdevcore.utils.getCacheFile
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import okhttp3.HttpUrl
import okhttp3.internal.Util
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class GlobalConfigModule private constructor(builder: Builder){

    private var mApiUrl: HttpUrl? = null
    private var mBaseUrl: BaseUrl? = null
    private var mCacheFile: File? = null
    private var mCacheFactory: Cache.Factory? = null
    private var mLoaderStrategy: ImageLoaderStrategy<ImageConfig>? = null
    private var mRetrofitConfigurations: List<ClientModule.RetrofitConfiguration>? = null
    private var mOkHttpConfigurations: List<ClientModule.OkHttpConfiguration>? = null
    private var mHandler: GlobalHttpHandler? = null
    private var mErrorListener: ResponseErrorListener? = null
    private var mExecutorService: ExecutorService? = null
    private var mRxCacheConfiguration: ClientModule.RxCacheConfiguration? = null
    private var mGsonConfigurations: List<AppModule.GsonConfiguration>? = null
    private var mImageLoaderInterceptors: List<ImageLoaderInterceptor<ImageConfig>>? = null
    private var mRxProgressConfiguration: AppModule.RxProgressConfiguration?=null

    init {
        this.mApiUrl = builder.apiUrl
        this.mBaseUrl = builder.url
        this.mCacheFile = builder.cacheFile
        this.mCacheFactory = builder.cacheFactory
        this.mLoaderStrategy = builder.loaderStrategy
        this.mRetrofitConfigurations = builder.retrofitConfigurations
        this.mOkHttpConfigurations = builder.okHttpConfigurations
        this.mErrorListener = builder.responseErrorListener
        this.mHandler = builder.handler
        this.mExecutorService = builder.executorService
        this.mRxCacheConfiguration = builder.rxCacheConfiguration
        this.mGsonConfigurations = builder.gsonConfigurations
        this.mImageLoaderInterceptors = builder.imageLoaderInterceptors
        this.mRxProgressConfiguration = builder.rxProgressConfiguration
    }

    fun provideBaseUrl(): HttpUrl {
        val url = mBaseUrl?.url()
        return url?:(mApiUrl ?: throw AssertionError("url empty"))
    }

    fun provideCacheFile(application: Application): File {
        return mCacheFile?:getCacheFile(application)
    }

    fun provideCacheFactory(application: Application): Cache.Factory{
        return mCacheFactory?: CacheFactory(application)
    }

    fun provideImageLoaderStrategy(): ImageLoaderStrategy<ImageConfig>? {
        return mLoaderStrategy
    }

    fun provideRetrofitConfigurations(): List<ClientModule.RetrofitConfiguration>? {
        return mRetrofitConfigurations
    }

    fun provideOkHttpConfigurations(): List<ClientModule.OkHttpConfiguration>? {
        return mOkHttpConfigurations
    }

    fun provideGlobalHttpHandler(): GlobalHttpHandler {
        return mHandler?:GlobalHttpHandler.EMPTY
    }

    fun provideResponseErrorListener(): ResponseErrorListener {
        return mErrorListener ?: ResponseErrorListener.EMPTY
    }

    fun provideExecutorService(): ExecutorService {
        return mExecutorService ?: ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                SynchronousQueue(), Util.threadFactory("quick Executor", false))
    }

    fun provideRxCacheConfiguration(): ClientModule.RxCacheConfiguration? {
        return mRxCacheConfiguration
    }

    fun provideGsonConfigurations(): List<AppModule.GsonConfiguration>? {
        return mGsonConfigurations
    }

    fun provideImageLoaderInterceptors(): List<ImageLoaderInterceptor<ImageConfig>>{
        return mImageLoaderInterceptors?:ArrayList()
    }

    fun provideRxProgressConfiguration(): AppModule.RxProgressConfiguration{
        return mRxProgressConfiguration?:(object : AppModule.RxProgressConfiguration{
            override fun provideRxProgressObservable(msg:String,cancelable:Boolean): RxProgressObservable {
                return RxProgressObservableImpl(msg,cancelable)
            }
        })
    }

    class Builder{
        internal var apiUrl:HttpUrl? = null
        internal var url: BaseUrl? = null
        internal var cacheFile: File? = null
        internal var cacheFactory: Cache.Factory? = null
        internal var loaderStrategy: ImageLoaderStrategy<ImageConfig>? = null
        internal var retrofitConfigurations: ArrayList<ClientModule.RetrofitConfiguration>? = null
        internal var okHttpConfigurations: ArrayList<ClientModule.OkHttpConfiguration>? = null
        internal var handler: GlobalHttpHandler? = null
        internal var responseErrorListener: ResponseErrorListener? = null
        internal var executorService: ExecutorService? = null
        internal var rxCacheConfiguration: ClientModule.RxCacheConfiguration? = null
        internal var gsonConfigurations: ArrayList<AppModule.GsonConfiguration>? = null
        internal var imageLoaderInterceptors:ArrayList<ImageLoaderInterceptor<ImageConfig>>? = null
        internal var rxProgressConfiguration: AppModule.RxProgressConfiguration? = null

        fun baseUrl(baseUrl: String): Builder {//基础url
            if (TextUtils.isEmpty(baseUrl)) {
                throw NullPointerException("BaseUrl can not be empty")
            }
            this.apiUrl = HttpUrl.parse(baseUrl)
            return this
        }

        fun baseUrl(baseUrl: BaseUrl): Builder {
            this.url = baseUrl
            return this
        }

        fun cacheFile(cacheFile: File): Builder {
            this.cacheFile = cacheFile
            return this
        }

        fun cacheFactory(cacheFactory: Cache.Factory): Builder {
            this.cacheFactory = cacheFactory
            return this
        }

        fun imageLoaderStrategy(loaderStrategy: ImageLoaderStrategy<ImageConfig>): Builder {//用来请求网络图片
            this.loaderStrategy = loaderStrategy
            return this
        }

        fun addRetrofitConfiguration(retrofitConfiguration: ClientModule.RetrofitConfiguration): Builder {
            if(retrofitConfigurations == null){
                retrofitConfigurations = ArrayList()
            }
            retrofitConfigurations?.apply {
                add(retrofitConfiguration)
            }
            return this
        }

        fun addOkHttpConfiguration(okHttpConfiguration: ClientModule.OkHttpConfiguration): Builder {
            if(okHttpConfigurations == null){
                okHttpConfigurations = ArrayList()
            }
            okHttpConfigurations?.apply {
                add(okHttpConfiguration)
            }
            return this
        }

        fun responseErrorListener(listener: ResponseErrorListener): Builder {//处理所有RxJava的onError逻辑
            this.responseErrorListener = listener
            return this
        }

        fun globalHttpHandler(handler: GlobalHttpHandler): Builder {//用来处理http响应结果
            this.handler = handler
            return this
        }

        fun executorService(executorService: ExecutorService): Builder {
            this.executorService = executorService
            return this
        }

        fun rxCacheConfiguration(rxCacheConfiguration: ClientModule.RxCacheConfiguration): Builder {
            this.rxCacheConfiguration = rxCacheConfiguration
            return this
        }

        fun addGsonConfiguration(gsonConfiguration: AppModule.GsonConfiguration): Builder {
            if(gsonConfigurations == null){
                gsonConfigurations = ArrayList()
            }
            gsonConfigurations?.apply {
                add(gsonConfiguration)
            }
            return this
        }

        fun addImageLoaderInterceptor(imageLoaderInterceptor: ImageLoaderInterceptor<ImageConfig>): Builder {
            if(imageLoaderInterceptors == null){
                imageLoaderInterceptors = ArrayList()
            }
            imageLoaderInterceptors?.apply {
                add(imageLoaderInterceptor)
            }
            return this
        }

        fun rxProgressConfiguration(rxProgressConfiguration: AppModule.RxProgressConfiguration): Builder {
            this.rxProgressConfiguration = rxProgressConfiguration
            return this
        }

        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}
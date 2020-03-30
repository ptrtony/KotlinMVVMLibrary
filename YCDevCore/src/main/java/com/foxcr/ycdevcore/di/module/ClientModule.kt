package com.foxcr.ycdevcore.di.module

import android.app.Application
import android.content.Context
import android.system.Os.bind
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.foxcr.ycdevcore.integration.http.GlobalHttpHandler
import com.foxcr.ycdevcore.integration.http.RequestInterceptor
import com.foxcr.ycdevcore.utils.makeDirs
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class ClientModule {

    companion object {
        private const val CLIENT_MODULE_TAG = "clientModule"
        private const val TIME_OUT_SECONDS = 10

        val clientModule by lazy {
            Kodein.Module(CLIENT_MODULE_TAG) {

                bind<RxCache.Builder>() with provider { RxCache.Builder() }

                bind<File>(tag = "rxCacheDirectory") with provider {
                    makeDirs(File(instance<File>(tag = "cacheFile"), "RxCache"))
                }

                bind<RxCache>() with singleton {
                    var rxCache:RxCache?=null
                    val builder = instance<RxCache.Builder>()
                    val configuration = instance<GlobalConfigModule>().provideRxCacheConfiguration()
                    if (configuration != null) {
                        rxCache = configuration.configRxCache(instance<Application>(), builder)
                    }
                    rxCache?:builder.persistence(instance(tag = "rxCacheDirectory"), GsonSpeaker(instance()))
                }

                bind<Retrofit.Builder>() with provider { Retrofit.Builder() }

                bind<OkHttpClient.Builder>() with provider { OkHttpClient.Builder() }

                bind<GlobalHttpHandler>() with singleton { instance<GlobalConfigModule>().provideGlobalHttpHandler()}

                bind<RequestInterceptor>() with singleton { RequestInterceptor(instance()) }

                bind<RxErrorHandler>() with singleton {
                    RxErrorHandler
                            .builder()
                            .with(instance<Application>())
                            .responseErrorListener(instance<GlobalConfigModule>().provideResponseErrorListener())
                            .build()
                }

                bind<Retrofit>() with singleton {
                    instance<Retrofit.Builder>()
                            .baseUrl(instance<GlobalConfigModule>().provideBaseUrl())
                            .client(instance())
                            .apply {
                                instance<GlobalConfigModule>().provideRetrofitConfigurations()?.run {
                                    forEach {
                                        it.configRetrofit(instance<Application>(),this@apply)
                                    }
                                }
                            }
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addCallAdapterFactory(CoroutineCallAdapterFactory())
                            .addConverterFactory(GsonConverterFactory.create(instance()))
                            .build()
                }

                bind<OkHttpClient>() with singleton {
                    instance<OkHttpClient.Builder>()
                            .connectTimeout(
                                    TIME_OUT_SECONDS.toLong(),
                                    TimeUnit.SECONDS)
                            .readTimeout(
                                    TIME_OUT_SECONDS.toLong(),
                                    TimeUnit.SECONDS)
                            .addNetworkInterceptor(instance<RequestInterceptor>())
                            .addInterceptor{
                                chain -> chain.proceed(instance<GlobalHttpHandler>().onHttpRequestBefore(chain, chain.request()))
                            }
                            .dispatcher(Dispatcher(instance<GlobalConfigModule>().provideExecutorService()))
                            .apply {
                                instance<GlobalConfigModule>().provideOkHttpConfigurations()?.run {
                                    forEach {
                                        it.configOkHttp(instance<Application>(),this@apply)
                                    }
                                }
                            }
                            .build()
                }
            }
        }
    }

    interface RetrofitConfiguration {
        fun configRetrofit(context: Context, builder: Retrofit.Builder)
    }
    interface OkHttpConfiguration {
        fun configOkHttp(context: Context, builder: OkHttpClient.Builder)
    }
    interface RxCacheConfiguration {
        fun configRxCache(context: Context, builder: RxCache.Builder): RxCache?
        fun offlineModel():Boolean
    }
}
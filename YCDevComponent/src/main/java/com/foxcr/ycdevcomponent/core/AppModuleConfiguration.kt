package com.foxcr.ycdevcomponent.core

import android.app.Application
import android.content.Context
import com.foxcr.cyextkt.BuildConfig
import com.foxcr.ycdevcomponent.constant.Constants
import com.foxcr.ycdevcomponent.core.factory.JsonConverterFactory
import com.foxcr.ycdevcomponent.module.initialModel
import com.foxcr.ycdevcomponent.service.serviceModel
import com.foxcr.ycdevcomponent.widget.RxProgressLoadingDialog
import com.foxcr.ycdevcore.base.delegate.AppLifecycle
import com.foxcr.ycdevcore.di.module.AppModule
import com.foxcr.ycdevcore.di.module.ClientModule
import com.foxcr.ycdevcore.di.module.GlobalConfigModule
import com.foxcr.ycdevcore.integration.ConfigModule
import com.foxcr.ycdevcore.integration.http.BaseUrl
import com.foxcr.ycdevcore.integration.rxsubscriber.RxProgressObservable
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
@author cjq
@Date 2020/5/19
@Time 上午11:25
@Describe: 配置网络相关
 */
class AppModuleConfiguration :ConfigModule{
    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.baseUrl(object:BaseUrl{
            override fun url(): HttpUrl? {
                return if (BuildConfig.DEBUG) {
                    HttpUrl.parse(Constants.TEST_APIS_URL)
                } else {
                    HttpUrl.parse(Constants.ONLINE_APIS_URL)

                }
            }

        })
            .globalHttpHandler(GloableHttpHeaderImpl())
            .responseErrorListener(ResponseErrorListenerImpl())
            .addGsonConfiguration(object : AppModule.GsonConfiguration {
                override fun configGson(context: Context, gsonBuilder: GsonBuilder) {
                    gsonBuilder
                        .serializeNulls()
                        .enableComplexMapKeySerialization()
                }

            })
            .addOkHttpConfiguration(object : ClientModule.OkHttpConfiguration {
                override fun configOkHttp(context: Context, builder: OkHttpClient.Builder) {
                    builder.apply {
                        connectTimeout(15L, TimeUnit.SECONDS)
                        readTimeout(15L, TimeUnit.SECONDS)
                    }
                }


            })

            .addRetrofitConfiguration(object : ClientModule.RetrofitConfiguration {
                override fun configRetrofit(context: Context, builder: Retrofit.Builder) {
                    val gson by obtainAppKodeinAware().instance<Gson>()
                    builder.addConverterFactory(JsonConverterFactory.create(gson))//自定义JsonConverter，为加密解密预留入口
                }

            })
            .rxProgressConfiguration(object : AppModule.RxProgressConfiguration {
                override fun provideRxProgressObservable(msg: String, cancelable: Boolean): RxProgressObservable {
                    return RxProgressLoadingDialog(msg, cancelable)
                }

            })
    }

    override fun injectAppLifecycle(context: Context, lifecycles: ArrayList<AppLifecycle>) {

    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: ArrayList<Application.ActivityLifecycleCallbacks>
    ) {

    }

    override fun injectKoDeinModule(context: Context, kodeinModules: ArrayList<Kodein.Module>) {
        kodeinModules.add(serviceModel)
        kodeinModules.add(initialModel)
    }

    override val priority: Int
        get() = ConfigModule.LIB_MODULE


}
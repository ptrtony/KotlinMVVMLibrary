package com.foxcr.ycdevcomponent.core

import android.app.Application
import android.content.Context
import com.foxcr.ycdevcomponent.di.componentModule
import com.foxcr.ycdevcore.integration.ConfigModule
import com.foxcr.ycdevcore.base.delegate.AppLifecycle
import com.foxcr.ycdevcore.di.module.ClientModule
import com.foxcr.ycdevcore.di.module.GlobalConfigModule
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import timber.log.Timber

class ComponentConfigModule: ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.addOkHttpConfiguration(object : ClientModule.OkHttpConfiguration{
            override fun configOkHttp(context: Context, builder: OkHttpClient.Builder) {
                RetrofitUrlManager.getInstance().with(builder)
                builder.addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    Timber.d(it)
                }).apply {
                    val configModule by obtainAppKodeinAware().instance<DefaultConfigModule>()
                    level = when (configModule.enableOkHttpLog()) {
                        true -> HttpLoggingInterceptor.Level.BODY
                        false -> HttpLoggingInterceptor.Level.NONE
                    }
                })
            }
        })
    }

    override fun injectAppLifecycle(context: Context, lifecycles: ArrayList<AppLifecycle>) {
        lifecycles.add(AppLifecycleImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: ArrayList<Application.ActivityLifecycleCallbacks>) {
        lifecycles.add(ActivityLifecycleCallbacksImpl())
    }

    override fun injectKoDeinModule(context: Context, kodeinModules: ArrayList<Kodein.Module>) {
        kodeinModules.add(componentModule)
    }

    override val priority = Int.MIN_VALUE
}
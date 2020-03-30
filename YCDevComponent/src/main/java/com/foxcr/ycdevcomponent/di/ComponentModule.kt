package com.foxcr.ycdevcomponent.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.foxcr.ycdevcomponent.core.DefaultConfigModule
import com.foxcr.ycdevcomponent.core.IRetrofitUrlManagerService
import com.foxcr.ycdevcomponent.core.ManifestParser
import com.foxcr.ycdevcomponent.core.RetrofitUrlManagerServiceImpl
import com.foxcr.ycdevcomponent.http.ResponseTransformerStrategy
import com.foxcr.ycdevcomponent.utils.isApkInDebug
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

private const val MODEL_MODULE_TAG = "ComponentModule"

val componentModule = Kodein.Module(MODEL_MODULE_TAG) {

    bind<IRetrofitUrlManagerService>() with singleton {
        RetrofitUrlManagerServiceImpl()
    }

    bind<SharedPreferences>(tag = "quickDev defaultSharedPreferences") with singleton {
        PreferenceManager.getDefaultSharedPreferences(instance())
    }

    bind<RxSharedPreferences>() with singleton {
        RxSharedPreferences.create(instance<SharedPreferences>(tag = "quickDev defaultSharedPreferences"))
    }

    bind<DefaultConfigModule>() with singleton {
        val modules = ManifestParser(instance()).parse()
        if(modules.isEmpty()){
            DefaultConfigModule.builder()
                .enableLeakCanary(instance<Application>().isApkInDebug())
                .enableOkHttpLog(instance<Application>().isApkInDebug())
                .enableDoraemonKit(false)
                .build()
        }else{
            val builder = DefaultConfigModule.builder()
            modules[0].applyOptions(instance(),builder)
            builder.build()
        }
    }

    bind<ResponseTransformerStrategy>() with provider {
        instance<DefaultConfigModule>().provideResponseTransformerStrategy()
    }

}
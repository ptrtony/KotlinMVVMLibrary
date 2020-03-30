package com.foxcr.ycdevimageloader

import android.app.Application
import android.content.Context
import com.foxcr.ycdevcore.base.delegate.AppLifecycle
import com.foxcr.ycdevcore.di.module.GlobalConfigModule
import com.foxcr.ycdevcore.integration.ConfigModule
import org.kodein.di.Kodein

class GlideConfigModule: ConfigModule {
    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {

    }

    override fun injectAppLifecycle(context: Context, lifecycles: ArrayList<AppLifecycle>) {
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: ArrayList<Application.ActivityLifecycleCallbacks>) {
    }

    override fun injectKoDeinModule(context: Context, kodeinModules: ArrayList<Kodein.Module>) {
        kodeinModules.add(glideKodeinModule)
    }

    override val priority = Int.MAX_VALUE
}
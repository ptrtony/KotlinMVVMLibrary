package com.foxcr.ycdevvm

import android.app.Application
import android.content.Context
import com.foxcr.ycdevcore.base.delegate.AppLifecycle
import com.foxcr.ycdevcore.di.module.GlobalConfigModule
import com.foxcr.ycdevcore.integration.ConfigModule
import org.kodein.di.Kodein

class VMConfigModule: ConfigModule {
    override val priority: Int = ConfigModule.LIB_MODULE

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: ArrayList<Application.ActivityLifecycleCallbacks>) {
        lifecycles.add(ActivityLifecycleImpl())
    }

    override fun injectAppLifecycle(context: Context, lifecycles: ArrayList<AppLifecycle>) {

    }

    override fun injectKoDeinModule(context: Context, kodeinModules: ArrayList<Kodein.Module>) {
    }
}
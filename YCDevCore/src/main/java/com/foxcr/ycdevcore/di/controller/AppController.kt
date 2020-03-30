package com.foxcr.ycdevcore.di.controller

import android.app.Application
import android.content.Context
import android.system.Os.bind
import com.foxcr.ycdevcore.di.module.AppModule
import com.foxcr.ycdevcore.di.module.ClientModule
import com.foxcr.ycdevcore.di.module.GlobalConfigModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton
import java.util.Collections.singleton

class AppController(application: Application, globalConfigModule: GlobalConfigModule, kodeinModules: ArrayList<Kodein.Module>) : KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        bind<Context>() with singleton { application}
        bind<GlobalConfigModule>() with singleton { globalConfigModule }
        import(androidXModule(application))
        import(AppModule.appModule)
        import(ClientModule.clientModule)
        kodeinModules.forEach {
            import(it)
        }
    }
    override val kodeinContext = kcontext(application)

}


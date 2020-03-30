package com.foxcr.ycdevcomponent.core

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoraemonKit
import com.foxcr.ycdevcomponent.utils.isApkInDebug
import com.foxcr.ycdevcore.base.delegate.AppLifecycle
import com.foxcr.ycdevcore.integration.cache.Cache
import com.foxcr.ycdevcore.integration.cache.IntelligentCache
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import es.dmoral.toasty.Toasty
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import org.kodein.di.generic.instance

class AppLifecycleImpl: AppLifecycle {

    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        if (application.isApkInDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()    // 打印日志
            ARouter.openDebug()  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application) // 尽可能早，推荐在Application中初始化

        DefaultUrlParserEx().apply {
            this.init(RetrofitUrlManager.getInstance())
            RetrofitUrlManager.getInstance().setUrlParser(this)
        }

        Toasty.Config.getInstance().allowQueue(false).apply()

        val configModule by obtainAppKodeinAware().instance<DefaultConfigModule>()
        val enableLeakCanary = configModule.enableLeakCanary()
        val enableDoraemonKit = configModule.enableDoraemonKit()

        val mRefWatcher = if (enableLeakCanary) LeakCanary.install(application) else RefWatcher.DISABLED
        val cache: Cache<String, Any> by obtainAppKodeinAware().instance()
        cache.put(IntelligentCache.getKeyOfKeep(RefWatcher::class.java.name),mRefWatcher as Any)

        if(enableDoraemonKit)
            DoraemonKit.install(application)

    }

    override fun onLowMemory() {
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
    }

    override fun onTrimMemory(level: Int) {
    }

}
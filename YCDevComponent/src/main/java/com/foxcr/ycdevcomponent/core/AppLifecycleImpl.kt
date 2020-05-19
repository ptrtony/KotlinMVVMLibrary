package com.foxcr.ycdevcomponent.core

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoraemonKit
import com.foxcr.ycdevcomponent.BuildConfig
import com.foxcr.ycdevcomponent.R
import com.foxcr.ycdevcomponent.constant.Constants
import com.foxcr.ycdevcomponent.service.ApiService
import com.foxcr.ycdevcomponent.utils.RxPreferences
import com.foxcr.ycdevcomponent.utils.isApkInDebug
import com.foxcr.ycdevcore.base.delegate.AppLifecycle
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import com.foxcr.ycdevvm.widget.UniClassicsFooter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogcatLogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import es.dmoral.toasty.Toasty
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.HttpUrl
import org.json.JSONArray
import org.json.JSONObject
import org.kodein.di.generic.instance
import timber.log.Timber
import java.lang.Exception

class AppLifecycleImpl: AppLifecycle {

    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {
//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return
//        }

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
//        val enableLeakCanary = configModule.enableLeakCanary()
        val enableDoraemonKit = configModule.enableDoraemonKit()

//        val mRefWatcher = if (enableLeakCanary) LeakCanary.install(application) else RefWatcher.DISABLED
//        val cache: Cache<String, Any> by obtainAppKodeinAware().instance()
//        cache.put(IntelligentCache.getKeyOfKeep(RefWatcher::class.java.name),mRefWatcher as Any)

        if(enableDoraemonKit)
            DoraemonKit.install(application)

        //日志框架 初始化
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(0)         // (Optional) How many method line to show. Default 2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
            .logStrategy(LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
            .tag("quick-kotlin-demo")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        Timber.plant(object : Timber.DebugTree() {
            override fun isLoggable(tag: String?, priority: Int): Boolean {
                return BuildConfig.DEBUG
            }
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                try {
                    when {
                        message.startsWith("{") -> {
                            JSONObject(message)
                            Logger.json(message)
                        }
                        message.startsWith("[") -> {
                            JSONArray(message)
                            Logger.json(message)
                        }
                        else -> Logger.log(priority, tag, message, t)
                    }
                } catch (e: Exception) {
                    Logger.log(priority, tag, message, t)
                }
            }
        })

        if(BuildConfig.DEBUG){
            val buildType = RxPreferences.getInteger("BuildType",0)
            if(buildType.get() == 0){
                buildType.set(1)
            }
            val urlManagerService by obtainAppKodeinAware().instance<IRetrofitUrlManagerService>()
            urlManagerService.apply {
                //设置映射，domainName为Api请求接口上配置的Domain-Name的值，这里是user，具体可以查看UserService
                //设置映射后，对应的接口请求网络时，如果有通过addUrlParserListener添加回调，框架会自动获取当前请求的url，需要替换成的url，执行回调
                //可在回调方法里，根据业务自行替换，可替换域名，也可替换域名后的端口，path等，随意替换，返回的HttpUrl将作为最终的请求地址
                //如果没有通过addUrlParserListener添加回调，那么会执行默认逻辑，单纯的进行域名替换，无法替换path等后续部分的数据
                //每一个module都可以配置，支持每个module都有自己的Api请求地址，实现更大自由的多模块开发
                //支持动态切换，为生产环境和测试环境动态切换提供技术支持
                //为了演示，本demo特意配置retrofit库的url为https://baidu.com，配置代码在AppModuleConfiguration，这里动态替换成https://api.github.com
                putDomain(ApiService.DOMAIN_NAME, Constants.ONLINE_APIS_URL)
                addUrlParserListener(ApiService.DOMAIN_NAME,object : OnUrlParserListener {
                    override fun parseUrl(domainUrl: HttpUrl?, url: HttpUrl): HttpUrl? {
                        if(domainUrl == null) return url
                        return if(buildType.get() == 1){
                            HttpUrl.parse(url.toString().replace(Constants.ONLINE_APIS_URL,Constants.TEST_APIS_URL))
                        }else{
                            HttpUrl.parse(url.toString().replace(Constants.TEST_APIS_URL,Constants.ONLINE_APIS_URL))
                        }
                    }
                })
            }
        }

        //SmartRefreshLayout 初始化
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            MaterialHeader(context).apply {
                setShowBezierWave(true)
                setColorSchemeResources(R.color.main_color)
            }
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator{ context, layout ->
            UniClassicsFooter(context).setDrawableSize(20f)
        }

    }

    override fun onLowMemory() {
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
    }

    override fun onTrimMemory(level: Int) {
    }

}
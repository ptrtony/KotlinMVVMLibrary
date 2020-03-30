package com.foxcr.ycdevcore.base.delegate

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.text.TextUtils
import com.foxcr.ycdevcore.base.App
import com.foxcr.ycdevcore.di.controller.AppController
import com.foxcr.ycdevcore.di.module.GlobalConfigModule
import com.foxcr.ycdevcore.integration.ActivityLifecycle
import com.foxcr.ycdevcore.integration.AppManager
import com.foxcr.ycdevcore.integration.ConfigModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import java.lang.reflect.InvocationTargetException
import kotlin.collections.ArrayList

class AppDelegate : App, AppLifecycle, KodeinAware {

    private lateinit var context: Context
    private lateinit var mAppController: AppController
    private var mModules: ArrayList<ConfigModule>?=null
    private var mAutoRegisterModules: ArrayList<ConfigModule>?=null
    private lateinit var mAppLifecycleList: ArrayList<AppLifecycle>
    private lateinit var mActivityLifecycleList: ArrayList<Application.ActivityLifecycleCallbacks>
    private var isInitializer = false

    override val kodein: Kodein by lazy {
        getAppController().kodein
    }

    @Synchronized
    internal fun init(context: Context): AppDelegate {
        if(isInitializer) return instance
        this.context = context
        loadAutoRegister()
        this.mModules = mAutoRegisterModules
        this.mActivityLifecycleList = ArrayList()
        this.mAppLifecycleList = ArrayList()
        this.mModules?.run {
            //根据优先级排序
            sortBy {
                it.priority
            }
            forEach {
                it.run {
                    injectAppLifecycle(context, mAppLifecycleList)
                    injectActivityLifecycle(context, mActivityLifecycleList)
                }
            }
        }
        isInitializer = true
        return instance
    }

    override fun attachBaseContext(base: Context) {
        for (lifecycle in mAppLifecycleList) {
            lifecycle.attachBaseContext(base)
        }
    }

    private fun loadAutoRegister(){

    }

    private fun register(className:String) {
        if (!TextUtils.isEmpty(className)) {
            try {
                val clazz = Class.forName(className)
                val obj = clazz.getConstructor().newInstance()
                if (obj is ConfigModule) {
                    mAutoRegisterModules = (mAutoRegisterModules?:ArrayList())
                    mAutoRegisterModules?.add(obj)
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

        }
    }

    override fun onCreate(application: Application) {
        mAppController = AppController(application,getGlobalConfigModule(application,mModules),getKodeinModules(mModules))
        this.mModules = null
        application.registerComponentCallbacks(this)
        AppManager.instance.init(application)
        application.registerActivityLifecycleCallbacks(ActivityLifecycle(application))
        for (lifecycle in mActivityLifecycleList) {
            application.registerActivityLifecycleCallbacks(lifecycle)
        }
        for (lifecycle in mAppLifecycleList) {
            lifecycle.onCreate(application)
        }
    }

    override fun getAppController(): AppController = mAppController

    private fun getGlobalConfigModule(context: Context, modules: List<ConfigModule>?): GlobalConfigModule {
        val builder = GlobalConfigModule
                .builder()
        modules?.forEach {
            it.applyOptions(context, builder)
        }
        return builder.build()
    }

    private fun getKodeinModules(modules: List<ConfigModule>?):ArrayList<Kodein.Module>{
        val kodeinModules: ArrayList<Kodein.Module> = ArrayList()
        modules?.forEach {
            it.injectKoDeinModule(context,kodeinModules)
        }
        return kodeinModules
    }

    override fun onLowMemory() {
        for (lifecycle in mAppLifecycleList) {
            lifecycle.onLowMemory()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        for (lifecycle in mAppLifecycleList) {
            lifecycle.onConfigurationChanged(newConfig)
        }
    }

    override fun onTrimMemory(level: Int) {
        for (lifecycle in mAppLifecycleList) {
            lifecycle.onTrimMemory(level)
        }
    }

    companion object {
        val instance: AppDelegate by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppDelegate()
        }
    }
}
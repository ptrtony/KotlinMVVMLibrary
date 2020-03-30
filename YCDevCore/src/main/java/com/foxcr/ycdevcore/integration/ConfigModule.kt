package com.foxcr.ycdevcore.integration

import android.app.Application
import android.content.Context
import com.foxcr.ycdevcore.base.delegate.AppLifecycle
import com.foxcr.ycdevcore.di.module.GlobalConfigModule
import org.kodein.di.Kodein


/**
 * ================================================
 * [ConfigModule] 可以给框架配置一些参数,需要实现 [ConfigModule] 后,在 AndroidManifest 中声明该实现类
 * ================================================
 */
interface ConfigModule {
    /**
     * 使用[GlobalConfigModule.Builder]给框架配置一些配置参数
     *
     * @param context
     * @param builder
     */
    fun applyOptions(context: Context, builder: GlobalConfigModule.Builder)

    /**
     * 使用[AppLifecycles]在Application的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    fun injectAppLifecycle(context: Context, lifecycles: ArrayList<AppLifecycle>)

    /**
     * 使用[Application.ActivityLifecycleCallbacks]在Activity的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    fun injectActivityLifecycle(context: Context, lifecycles: ArrayList<Application.ActivityLifecycleCallbacks>)

    /**
     * 使用[kodeinModule]在AppController中import 依赖
     *
     * @param context
     * @param kodeinModule
     */
    fun injectKoDeinModule(context: Context, kodeinModules: ArrayList<Kodein.Module>)

    /**
     * config module 配置的优先级
     *
     */
    val priority:Int

    companion object {
        const val APP_MODULE = 0
        const val LIB_MODULE = 1
    }
}

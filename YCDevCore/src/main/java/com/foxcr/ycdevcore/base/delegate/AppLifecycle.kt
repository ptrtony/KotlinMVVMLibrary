package com.foxcr.ycdevcore.base.delegate

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context

/**
 * ================================================
 * 用于代理 [Application] 的生命周期
 * ================================================
 */
interface AppLifecycle : ComponentCallbacks2 {
    fun attachBaseContext(base: Context)
    fun onCreate(application: Application)
}

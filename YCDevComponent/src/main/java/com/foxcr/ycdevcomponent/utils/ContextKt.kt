package com.foxcr.ycdevcomponent.utils

import android.content.Context
import android.content.pm.ApplicationInfo

fun Context.isApkInDebug():Boolean {
    return try {
        val info = applicationInfo
        (info.flags and  ApplicationInfo.FLAG_DEBUGGABLE) != 0
    } catch (e:Exception) {
        false
    }
}
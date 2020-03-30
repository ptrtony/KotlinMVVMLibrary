package com.foxcr.ycdevcomponent.core

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

import java.util.ArrayList

class ManifestParser(private val context: Context) {

    fun parse(): List<AdvanceConfigModule> {
        val modules = ArrayList<AdvanceConfigModule>()
        try {
            val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            if (appInfo.metaData != null) {
                for (key in appInfo.metaData.keySet()) {
                    if (MODULE_VALUE == appInfo.metaData.get(key)) {
                        modules.add(parseModule(key))
                        break
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Unable to find metadata to parse AdvanceConfigModule", e)
        }

        return modules
    }

    companion object {
        private val MODULE_VALUE = "AdvanceConfigModule"

        private fun parseModule(className: String): AdvanceConfigModule {
            val clazz: Class<*>
            try {
                clazz = Class.forName(className)
            } catch (e: ClassNotFoundException) {
                throw IllegalArgumentException("Unable to find AdvanceConfigModule implementation", e)
            }

            val module: Any
            try {
                module = clazz.newInstance()
            } catch (e: InstantiationException) {
                throw RuntimeException("Unable to instantiate AdvanceConfigModule implementation for $clazz", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Unable to instantiate AdvanceConfigModule implementation for $clazz", e)
            }

            if (module !is AdvanceConfigModule) {
                throw RuntimeException("Expected instanceof AdvanceConfigModule, but found: $module")
            }
            return module as AdvanceConfigModule
        }
    }
}
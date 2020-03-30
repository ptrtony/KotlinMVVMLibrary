package com.foxcr.ycdevcore.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException

/**
 * Created by Tony Shen on 2017/1/20.
 */

fun exists(file: File?) = file != null && file.exists()

/**
 * 判断是否文件
 * @param file
 *
 * @return
 */
fun isFile(file: File) = exists(file) && file.isFile

/**
 * 判断是否目录
 * @param file
 *
 * @return
 */
fun isDirectory(file: File) = exists(file) && file.isDirectory

/**
 * 判断目录是否存在，不存在则判断是否创建成功
 *
 * @param file 文件
 * @return boolean
 */
fun createOrExistsDir(file: File?) = (file != null) && if (file.exists()) file.isDirectory else file.mkdirs()

/**
 * 判断文件是否存在，不存在则判断是否创建成功
 *
 * @param file 文件
 * @return boolean
 */
fun createOrExistsFile(file: File?): Boolean {
    if (file == null || !createOrExistsDir(file.parentFile)) {
        return false
    }
    if (file.exists()) {
        return file.isFile
    }
    return try {
        file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

/**
 * 创建未存在的文件夹
 *
 * @param file
 * @return
 */
fun makeDirs(file: File): File {
    if (!file.exists()) {
        file.mkdirs()
    }
    return file
}

fun getCacheFile(context: Context): File {
    return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        var file: File? = null
        file = context.externalCacheDir//获取系统管理的sd卡缓存文件
        if (file == null) {//如果获取的文件为空,就使用自己定义的缓存文件夹做缓存路径
            file = File(getCacheFilePath(context))
            makeDirs(file)
        }
        file
    } else {
        context.cacheDir
    }
}

/**
 * 获取自定义缓存文件地址
 *
 * @param context
 * @return
 */
fun getCacheFilePath(context: Context): String {
    val packageName = context.packageName
    return Environment.getExternalStorageDirectory().path + packageName
}
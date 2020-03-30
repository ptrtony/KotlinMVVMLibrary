package com.foxcr.ycdevimageloader

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder

interface GlideAppliesOptions {
    /**
     * 配置 @[Glide] 的自定义参数,此方法在 @[Glide] 初始化时执行(@[Glide] 在第一次被调用时初始化),只会执行一次
     *
     * @param context
     * @param builder [GlideBuilder] 此类被用来创建 Glide
     */
    fun applyGlideOptions(context: Context, builder: GlideBuilder)
}
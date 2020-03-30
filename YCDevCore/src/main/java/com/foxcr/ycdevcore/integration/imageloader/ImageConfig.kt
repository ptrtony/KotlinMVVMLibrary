package com.foxcr.ycdevcore.integration.imageloader

/**
 * ================================================
 * 这里是图片加载配置信息的基类,定义一些所有图片加载框架都可以用的通用参数
 * 每个 [ImageLoaderStrategy] 应该对应一个 [ImageConfig] 实现类
 * ================================================
 */

open class ImageConfig {
    var url: String=""
    var placeholder: Int = 0
    var errorPic: Int = 0
    var loaderCallBack : ImageLoaderCallBack? =null
}

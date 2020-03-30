package com.foxcr.ycdevimageloader

import com.foxcr.ycdevcore.integration.imageloader.ImageConfig


class GlideImageConfig: ImageConfig(){
    var cacheStrategy: Int = 0//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    var isCenterCrop: Boolean = false//是否将图片剪切为 CenterCrop
    var isCircle: Boolean = false//是否将图片剪切为圆形
    var imageRadius: Int = 0 //圆角角度
    var targetWidth: Int = 0
    var targetHeight: Int = 0
    fun isImageRadius(): Boolean {
        return imageRadius > 0
    }
}
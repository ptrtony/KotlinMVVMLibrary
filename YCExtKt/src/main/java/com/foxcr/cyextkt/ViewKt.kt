package com.foxcr.cyextkt

import android.view.View
import com.foxcr.ycdevcore.integration.imageloader.ImageConfig
import com.foxcr.ycdevcore.integration.imageloader.ImageLoader
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

//2秒内只响应一次点击事件，防抖机制
fun View.throttleFirstClicks(duration :Long=2): Observable<Unit> {
    return this.clicks().throttleFirst(duration, TimeUnit.SECONDS)
}


fun View.loadImage(imageConfig: ImageConfig){
    val imageLoader by obtainAppKodeinAware().instance<ImageLoader>()
    imageLoader.loadImage(this,imageConfig)
}

package com.foxcr.ycdevvm.base.adapter

import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.foxcr.ycdevcore.integration.imageloader.ImageLoader
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

abstract class BaseBindingItemProvider<T,B : ViewDataBinding>: BaseItemProvider<T, BaseBindingViewHolder<B>>(), KodeinAware {
    override val kodein: Kodein = obtainAppKodeinAware().kodein

    internal fun viewRecycled(holder: BaseBindingViewHolder<ViewDataBinding>) {
        val helper = holder as BaseBindingViewHolder<B>
        onViewRecycled(helper)
    }

    open fun onViewRecycled(holder: BaseBindingViewHolder<B>) {
        val imageLoader: ImageLoader by instance()
        clearImage(holder,imageLoader)
    }

    open fun clearImage(holder: BaseBindingViewHolder<B>, imageLoader: ImageLoader){

    }
}
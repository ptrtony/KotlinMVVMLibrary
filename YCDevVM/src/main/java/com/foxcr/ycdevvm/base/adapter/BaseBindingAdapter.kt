package com.foxcr.ycdevvm.base.adapter

open class BaseBindingAdapter<T>(data:List<T>?,init:(BaseBindingAdapter<T>.()->Unit)):
    BaseBindingMultipleAdapter<T>(data) {
    var provider: BaseBindingItemProvider<T, *>?=null

    init {
        init()
        finishInitialize()
    }

    override fun registerItemProvider() {
        provider?.let {
            mProviderDelegate.registerProvider(it)
        }
    }

    override fun getViewType(t: T) = (provider as BaseBindingItemProvider<T, *>).viewType()


}
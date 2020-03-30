package com.foxcr.ycdevvm.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.MultipleItemRvAdapter

abstract class BaseBindingMultipleAdapter<T>(data:List<T>?): MultipleItemRvAdapter<T, BaseBindingViewHolder<ViewDataBinding>>(data) {

    override fun createBaseViewHolder(view: View): BaseBindingViewHolder<ViewDataBinding> {
        return BaseBindingViewHolder(view)
    }

    override fun createBaseViewHolder(parent: ViewGroup?, layoutResId: Int): BaseBindingViewHolder<ViewDataBinding> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
        val view: View
        view = binding?.root ?: getItemView(layoutResId, parent)
        val holder = BaseBindingViewHolder<ViewDataBinding>(view)
        holder.binding = binding
        return holder
    }

    override fun convert(helper: BaseBindingViewHolder<ViewDataBinding>, item: T) {
        super.convert(helper, item)
        helper.binding?.executePendingBindings()
    }

    override fun onViewRecycled(holder: BaseBindingViewHolder<ViewDataBinding>) {
        val itemProviderSparseArray = mProviderDelegate.itemProviders
        val itemProvider = itemProviderSparseArray.get(holder.itemViewType)
        if (itemProvider is BaseBindingItemProvider<*, *>) {
            itemProvider.viewRecycled(holder)
        }
        super.onViewRecycled(holder)
    }
}
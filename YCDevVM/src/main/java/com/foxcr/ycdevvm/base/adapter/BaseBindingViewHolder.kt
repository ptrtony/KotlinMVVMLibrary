package com.foxcr.ycdevvm.base.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder

open class BaseBindingViewHolder<B : ViewDataBinding>(view: View) : BaseViewHolder(view) {
    var binding: B? = null
}
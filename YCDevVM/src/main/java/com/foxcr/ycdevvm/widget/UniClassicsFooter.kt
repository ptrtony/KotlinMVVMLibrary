package com.foxcr.ycdevvm.widget

import android.content.Context
import android.util.AttributeSet
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter

/**
@author cjq
@Date 2020/5/16
@Time 下午6:09
@Describe:
 */
class UniClassicsFooter : ClassicsFooter {
    private var mOnFinishListener: OnFinishListener? = null
    private lateinit var textFinish: String
    private lateinit var textNothing: String

    constructor(context: Context) : super(context) {
        copyString()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        copyString()
    }

//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        copyString()
//    }

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        mOnFinishListener?.apply {
            mTextFinish = getTextFinish(textFinish, textNothing)
        }
        return super.onFinish(layout, success)
    }

    fun setOnFinishListener(onFinishListener: OnFinishListener) {
        this.mOnFinishListener = onFinishListener
    }

    private fun copyString() {
        textFinish = mTextFinish
        textNothing = mTextNothing
    }

    interface OnFinishListener {
        fun getTextFinish(textFinish: String, textNothing: String): String
    }
}

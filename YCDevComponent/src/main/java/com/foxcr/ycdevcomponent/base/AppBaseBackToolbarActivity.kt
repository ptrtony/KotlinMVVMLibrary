package com.foxcr.ycdevcomponent.base

import android.os.Bundle
import android.view.View
import com.foxcr.ycdevcomponent.base.backactivity.BackActivityHelper
import com.foxcr.ycdevcomponent.base.backactivity.SwipeBackLayout
import com.foxcr.ycdevvm.base.BaseViewModel

/**
@author cjq
@Date 2020/5/16
@Time 下午6:19
@Describe:
 */
abstract class AppBaseBackToolbarActivity <VM:BaseViewModel> : AppBaseToolbarActivity<VM>(){
    private lateinit var mHelper: BackActivityHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = BackActivityHelper(this)
        mHelper.onActivityCreate()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }

    override fun <T : View?> findViewById(id: Int): T {
        var view = super.findViewById<T>(id)
        if (view == null && mHelper != null) {
            view = mHelper.findViewById(id) as T
        }
        return view
    }

    fun getSwipeBackLayout() : SwipeBackLayout {
        return mHelper.swipeBackLayout
    }

    fun setSwipeBackEnable(enable:Boolean){
        getSwipeBackLayout().setSwipeBackEnable(enable)
    }



}
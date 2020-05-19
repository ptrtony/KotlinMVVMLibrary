package com.foxcr.ycdevcomponent.widget

import android.content.Context
import com.foxcr.ycdevcore.integration.rxsubscriber.RxProgressObservable

/**
@author cjq
@Date 2020/5/19
@Time 上午11:40
@Describe:
 */


class RxProgressLoadingDialog constructor(msg:String,cancelable: Boolean): RxProgressObservable(msg, cancelable){
    private var mLoadingDialog:LoadingDialog?=null
    override fun show(context: Context, msg: String) {
        if (mLoadingDialog == null){
            mLoadingDialog = LoadingDialog(context)
        }
        if (!mLoadingDialog!!.isShowing){
            mLoadingDialog!!.showDialog()
        }
    }

    override fun isShowing(): Boolean {
        if (mLoadingDialog == null){
            return false
        }

        return mLoadingDialog!!.isShowing
    }

    override fun dismiss() {
        if (mLoadingDialog!=null){
            mLoadingDialog!!.cancelDialog()
        }
    }

}
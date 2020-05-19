package com.foxcr.ycdevcomponent.base

import android.os.Bundle
import com.foxcr.cyextkt.quickConfig
import com.foxcr.ycdevcomponent.base.statusbar.StatusBarUIHelp
import com.foxcr.ycdevvm.base.BaseActivity
import com.foxcr.ycdevvm.base.BaseViewModel

/**
@author cjq
@Date 2020/5/16
@Time 下午5:16
@Describe:
 */
abstract class AppBaseActivity<VM : BaseViewModel> :BaseActivity<VM>(){
    protected var mAppToolbarDelegate: AppToolbarDelegate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUIHelp.getDefaultDelegate().onCreate(this, quickConfig {
            isDarkFont = getAppToolbarDelegate().isDarkStatusBar()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mAppToolbarDelegate = null
        StatusBarUIHelp.getDefaultDelegate().onDestroy(this)
    }

    override fun initParam(savedInstanceState: Bundle?) {
        super.initParam(savedInstanceState)
        mViewModel.saveData("AppToolbarDelegate",getAppToolbarDelegate())
    }

    protected fun refreshUIByViewModel(){
        mActivityDelegate.binding?.setVariable(initVariableId(),mViewModel)
    }

    protected fun refreshUI(variableId:Int,value:Any){
        mActivityDelegate.binding?.setVariable(variableId,value)
    }

    fun setAppToolbarDelegate(appToolbarDelegate:AppToolbarDelegate){
        this.mAppToolbarDelegate = appToolbarDelegate
    }

    fun getAppToolbarDelegate():AppToolbarDelegate{
        if(this.mAppToolbarDelegate == null){
            this.mAppToolbarDelegate = AppToolbarDelegate(this)
        }
        return this.mAppToolbarDelegate!!
    }
}
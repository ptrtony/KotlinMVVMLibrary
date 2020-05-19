package com.foxcr.ycdevcomponent.base

import android.os.Bundle
import com.foxcr.cyextkt.quickConfig
import com.foxcr.ycdevcomponent.base.statusbar.StatusBarUIHelp
import com.foxcr.ycdevvm.base.BaseFragment
import com.foxcr.ycdevvm.base.BaseViewModel

/**
@author cjq
@Date 2020/5/16
@Time 下午5:35
@Describe:
 */
abstract class AppBaseFragment<VM : BaseViewModel> : BaseFragment<VM>(){
    protected var isViewCreated:Boolean = false
    protected var isFirstLoad:Boolean = true
    protected var mAppToolbarDelegate: AppToolbarDelegate? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUIHelp.getDefaultDelegate().onCreate(this, quickConfig {
            isDarkFont = getAppToolbarDelegate().isDarkStatusBar()
        })
        isViewCreated = true
    }

    override fun initParam(savedInstanceState: Bundle?) {
        mViewModel.saveData("AppToolbarDelegate",getAppToolbarDelegate())
    }

    protected fun refreshUIByViewModel(){
        mFragmentDelegate.binding?.setVariable(initVariableId(),mViewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        mAppToolbarDelegate = null
        StatusBarUIHelp.getDefaultDelegate().onDestroy(this)
    }

    protected fun refreshUI(variableId:Int,value:Any){
        mFragmentDelegate.binding?.setVariable(variableId,value)
    }

    fun setAppToolbarDelegate(appToolbarDelegate:AppToolbarDelegate){
        this.mAppToolbarDelegate = appToolbarDelegate
    }

    fun getAppToolbarDelegate():AppToolbarDelegate{
        if(this.mAppToolbarDelegate == null){
            this.mAppToolbarDelegate = AppToolbarDelegate(requireContext())
        }
        return this.mAppToolbarDelegate!!
    }
}
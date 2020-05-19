package com.foxcr.yclibrary

import com.foxcr.ycdevcomponent.base.AppBaseActivity
import com.foxcr.ycdevvm.base.DataBind
import com.foxcr.yclibrary.viewmodel.MainViewModel

@DataBind
class MainActivity : AppBaseActivity<MainViewModel>() {

    override fun layout(): Int  = R.layout.activity_main

    override fun initVariableId(): Int {
        return BR.viewModel
    }





}

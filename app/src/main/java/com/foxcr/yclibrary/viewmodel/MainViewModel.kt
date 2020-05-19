package com.foxcr.yclibrary.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.foxcr.cyextkt.autoDisposable
import com.foxcr.cyextkt.ioToUI
import com.foxcr.cyextkt.observeOnUI
import com.foxcr.ycdevcomponent.utils.toasty
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import com.foxcr.ycdevvm.base.BaseViewModel
import com.foxcr.ycdevcomponent.module.UserModel
import com.jakewharton.rxrelay2.PublishRelay
import org.kodein.di.generic.instance

/**
@author cjq
@Date 2020/5/15
@Time 下午3:51
@Describe:
 */


class MainViewModel (application: Application) :  BaseViewModel(application){
    val usernameTextChangeObservable:PublishRelay<CharSequence> = PublishRelay.create()
    val passwordTextChangeObservable:PublishRelay<CharSequence> = PublishRelay.create()
    val loginClickObservable:PublishRelay<Unit> = PublishRelay.create()
    private var username:String = ""
    private var password:String = ""
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        usernameTextChangeObservable.observeOnUI()
            .autoDisposable(scopeProvider)
            .subscribe{
                username = it.toString().trim()
            }

        passwordTextChangeObservable.observeOnUI()
            .autoDisposable(scopeProvider)
            .subscribe{
                password = it.toString().trim()
            }

        loginClickObservable
            .observeOnUI()
            .autoDisposable(scopeProvider)
            .subscribe {
                onLogin(username,password)
//                ("用户名:" + username + "密码:" + password).toasty(Toasty.LENGTH_SHORT)
            }
    }

    private fun onLogin(username:String,password:String){
        val userModel by obtainAppKodeinAware().instance<UserModel>()
        userModel.register(username,password,password)
            .ioToUI()
            .autoDisposable(scopeProvider)
            .subscribe({
                it.nickname
                "注册成功".toasty()
            }, {
                it.printStackTrace()
            })

    }



}
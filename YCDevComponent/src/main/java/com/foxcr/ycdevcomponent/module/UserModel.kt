package com.foxcr.ycdevcomponent.module

import com.foxcr.cyextkt.ioToUI
import com.foxcr.ycdevcomponent.http.ResponseTransformerStrategy
import com.foxcr.ycdevcomponent.model.bean.LoginResp
import com.foxcr.ycdevcomponent.model.bean.RegisterResp
import com.foxcr.ycdevcomponent.service.ApiService
import com.foxcr.ycdevvm.base.BaseModel
import io.reactivex.Observable
import org.kodein.di.generic.instance

/**
@author cjq
@Date 2020/5/19
@Time 上午11:53
@Describe:
 */

class UserModel : BaseModel(){
    private val apiService by instance<ApiService>()
    private val mResponseTransformerStrategy by instance<ResponseTransformerStrategy>()

    fun login(username:String,password:String):Observable<LoginResp>{
        return apiService.login(username, password)
            .compose(mResponseTransformerStrategy.provideObservableTransformer())

    }


    fun register(username:String,password:String,repassword:String):Observable<RegisterResp>{
        return apiService.register(username, password, repassword)
            .compose(mResponseTransformerStrategy.provideObservableTransformer())
    }
}
package com.foxcr.ycdevcomponent.service

import com.foxcr.ycdevcomponent.http.entity.BaseResponseBean
import com.foxcr.ycdevcomponent.model.bean.LoginResp
import com.foxcr.ycdevcomponent.model.bean.RegisterResp
import com.foxcr.ycdevcomponent.service.ApiService.Companion.HEADER_API_DOMAIN_NAME
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

/**
@author cjq
@Date 2020/5/19
@Time 上午11:09
@Describe:
 */
interface ApiService {
    @Headers(HEADER_API_DOMAIN_NAME)
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username")username:String, @Field("password")password:String):Observable<BaseResponseBean<LoginResp>>

    @Headers(HEADER_API_DOMAIN_NAME)
    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username") username:String,@Field("password")password:String,@Field("repassword")repassword:String):Observable<BaseResponseBean<RegisterResp>>
    companion object {
        const val DOMAIN_NAME = "testapp"
        const val HEADER_API_DOMAIN_NAME = "Domain-Name: $DOMAIN_NAME"
    }
}
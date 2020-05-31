package com.foxcr.ycdevcomponent.service

import com.foxcr.ycdevcomponent.http.entity.BaseResponseBean
import com.foxcr.ycdevcomponent.model.bean.LoginTestResp
import com.foxcr.ycdevcomponent.model.bean.RegisterResp
import io.reactivex.Observable
import retrofit2.http.*

/**
@author cjq
@Date 2020/5/19
@Time 上午11:09
@Describe:
 */
interface ApiService {
    @Headers(HEADER_API_DOMAIN_NAME)
    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username") username:String,@Field("password")password:String,@Field("repassword")repassword:String):Observable<BaseResponseBean<RegisterResp>>

    @Headers(HEADER_API_DOMAIN_NAME)
    @GET("api/AppUser/LoginForApp")
    fun login(@Query("mobile") mobile:String,@Query("password")password:String):Observable<BaseResponseBean<LoginTestResp>>


    companion object {
        const val DOMAIN_NAME = "testapp"
        const val HEADER_API_DOMAIN_NAME = "Domain-Name: $DOMAIN_NAME"
    }
}
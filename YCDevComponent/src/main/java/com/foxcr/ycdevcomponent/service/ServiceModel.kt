package com.foxcr.ycdevcomponent.service

import com.foxcr.ycdevcore.integration.IRepositoryManager
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
@author cjq
@Date 2020/5/19
@Time 上午11:23
@Describe:
 */
private const val SERVICE_MODULE = "serviceModule"

val serviceModel = Kodein.Module(SERVICE_MODULE){
    bind<ApiService>() with singleton {
        instance<IRepositoryManager>().obtainRetrofitService(ApiService::class.java)
    }
}
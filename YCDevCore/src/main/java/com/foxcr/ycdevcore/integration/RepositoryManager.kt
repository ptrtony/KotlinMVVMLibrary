package com.foxcr.ycdevcore.integration

import android.app.Application
import com.foxcr.ycdevcore.di.module.GlobalConfigModule
import com.foxcr.ycdevcore.integration.cache.Cache
import com.foxcr.ycdevcore.integration.cache.CacheType
import com.foxcr.ycdevcore.utils.obtainAppKodeinAware
import com.mcxiaoke.koi.ext.isConnected
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.EvictDynamicKeyGroup
import io.rx_cache2.EvictProvider
import io.rx_cache2.internal.RxCache
import org.kodein.di.generic.instance
import retrofit2.Retrofit
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class RepositoryManager(val context: Application, cacheFactory: Cache.Factory):IRepositoryManager {

    private val mRetrofitServiceCache: Cache<String, Any> = cacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE)
    private var mCacheServiceCache: Cache<String, Any> = cacheFactory.build(CacheType.CACHE_SERVICE_CACHE)
    private val mRetrofitServiceProxyCache = HashMap<String,Any>()

    @Synchronized
    override fun <T> obtainRetrofitService(service: Class<T>): T {
        return createWrapperService(service)
    }

    @Synchronized
    override fun <T> obtainCacheService(cache: Class<T>): T {
        return Proxy.newProxyInstance(cache.classLoader, arrayOf<Class<*>>(cache),object : InvocationHandler {
            override fun invoke(proxy: Any?, method: Method, args: Array<Any>): Any? {
                var cacheService: T? = mCacheServiceCache[cache.name] as T
                if (cacheService == null) {
                    val rxCache by obtainAppKodeinAware().instance<RxCache>()
                    cacheService = rxCache.using(cache)
                    mCacheServiceCache.put(cache.name, cacheService!!)
                }
                return try {
                    val evictArgs = args.singleOrNull {
                        when (it) {
                            is EvictDynamicKey -> true
                            is EvictDynamicKeyGroup -> true
                            is EvictProvider -> true
                            else -> false
                        }
                    }
                    val globalConfigModule by obtainAppKodeinAware().instance<GlobalConfigModule>()
                    val offlineModel = globalConfigModule.provideRxCacheConfiguration()?.offlineModel()?:false
                    if(evictArgs != null && offlineModel && !context.isConnected()){
                        args.forEachIndexed {
                            index, any ->
                            when (any) {
                                is EvictDynamicKey ->{
                                    args[index] = EvictDynamicKey(false)
                                }
                                is EvictDynamicKeyGroup -> {
                                    args[index] = EvictDynamicKeyGroup(false)
                                }
                                is EvictProvider -> {
                                    args[index] = EvictProvider(false)
                                }
                            }
                        }
                    }
                    method.invoke(cacheService,*args)
                }catch (e:Exception){
                    e.printStackTrace()
                    null
                }
            }
        }) as T
    }

    private fun <T> createWrapperService(serviceClass: Class<T>): T {
        return getRetrofitService(serviceClass)
    }

    private fun <T> getRetrofitService(serviceClass: Class<T>): T {
        var retrofitService: T? = mRetrofitServiceCache[serviceClass.name] as T
        if (retrofitService == null) {
            val retrofit by obtainAppKodeinAware().instance<Retrofit>()
            retrofitService = retrofit.create(serviceClass)
            val proxy :T? = mRetrofitServiceProxyCache[serviceClass.name] as T
            if(proxy != null){
                (proxy as IRetrofitProxy<T>).mProxy = retrofitService
                retrofitService = Proxy.newProxyInstance(serviceClass.classLoader, arrayOf<Class<*>>(serviceClass)
                    ,RetrofitProxyRetrofitProxy(proxy,(proxy as IRetrofitProxy<T>).mProxy)) as T
            }
            mRetrofitServiceCache.put(serviceClass.name, retrofitService!!)
        }
        return retrofitService
    }

    override fun clearAllCache() {
        val rxCache by obtainAppKodeinAware().instance<RxCache>()
        rxCache.evictAll().subscribe()
    }

    override fun <T> addRetrofitServiceProxy(service: Class<T>, serviceImpl: T){
        mRetrofitServiceProxyCache[service.name] = serviceImpl!! as Any
    }

    class RetrofitProxyRetrofitProxy<T>(private val serviceImpl:T, private val retrofitServiceImpl:T):InvocationHandler{

        override fun invoke(proxy: Any?, method: Method, args: Array<Any>?): Any? {
            val simulation = method.getAnnotation(ApiProxy::class.java)
            var result:Any?=null
            simulation?.apply {
                if(simulation.value){
                    result = if(args!=null){
                        method.invoke(serviceImpl,*args)
                    }else{
                        method.invoke(serviceImpl)
                    }
                }
            }
            if(result==null){
                result = if(args!=null){
                    method.invoke(retrofitServiceImpl,*args)
                }else{
                    method.invoke(retrofitServiceImpl)
                }
            }
            return result
        }

    }
}
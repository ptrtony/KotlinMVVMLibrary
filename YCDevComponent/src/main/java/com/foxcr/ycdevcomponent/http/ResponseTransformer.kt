package com.foxcr.ycdevcomponent.http

import com.foxcr.ycdevcomponent.http.entity.BaseResponseBean
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function

class ResponseTransformer<T> :ObservableTransformer<BaseResponseBean<T>,T>{

    override fun apply(upstream: Observable<BaseResponseBean<T>>): ObservableSource<T> {
        return upstream.flatMap(object :Function<BaseResponseBean<T>,ObservableSource<T>>{
            override fun apply(t: BaseResponseBean<T>): ObservableSource<T> {
                return if (t.success) {
                    Observable.just(t.data)
                }else{
                    Observable.error(ServerException(t.message,t.code))
                }
            }
        })
    }
}
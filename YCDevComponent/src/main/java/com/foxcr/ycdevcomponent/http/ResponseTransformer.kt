package com.foxcr.ycdevcomponent.http

import com.foxcr.ycdevcomponent.http.entity.BaseResponseBean
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class ResponseTransformer<T> :ObservableTransformer<BaseResponseBean<T>,T>{

    override fun apply(upstream: Observable<BaseResponseBean<T>>): ObservableSource<T> {
        return upstream.flatMap { t ->
            if (t.IsSuccess) {
                Observable.just(t.Data)
            }else{
                Observable.error(ServerException(t.Message,t.Code))
            }
        }
    }
}
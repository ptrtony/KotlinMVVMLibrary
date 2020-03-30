package com.foxcr.ycdevcomponent.http

import com.foxcr.ycdevcomponent.http.entity.ResponseBean
import io.reactivex.ObservableTransformer

class ResponseTransformerStrategyImpl :ResponseTransformerStrategy{
    override fun <T> provideObservableTransformer(): ObservableTransformer<ResponseBean<T>, T> {
        return ResponseTransformer<T>() as ObservableTransformer<ResponseBean<T>, T>
    }
}
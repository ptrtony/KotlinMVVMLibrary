package com.foxcr.ycdevcomponent.http

import com.foxcr.ycdevcomponent.http.entity.ResponseBean
import io.reactivex.ObservableTransformer

interface ResponseTransformerStrategy{
    fun <T> provideObservableTransformer():ObservableTransformer<ResponseBean<T>,T>
}
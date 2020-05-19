package com.foxcr.ycdevcomponent.event

import androidx.annotation.Keep

/**
@author cjq
@Date 2020/5/16
@Time 下午5:44
@Describe:
 */


@Keep
class DataToAdapterEvent<T> {
    var success:Boolean=true
    var data:T?=null
    var isLoadMore = false
}
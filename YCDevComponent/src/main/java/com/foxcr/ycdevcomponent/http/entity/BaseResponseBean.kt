package com.foxcr.ycdevcomponent.http.entity

class BaseResponseBean<T>:ResponseBean<T>() {
    var Code: Int=0
    var Message: String=""
    var IsSuccess: Boolean=false
    var DataCount:Int = 0
}